package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.OrderItemMapper;
import com.fire.firepalaceorderingsys.mapper.OrderMapper;
import com.fire.firepalaceorderingsys.pojo.Order;
import com.fire.firepalaceorderingsys.pojo.OrderItem;
import com.fire.firepalaceorderingsys.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 添加菜品到购物车
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderItem addToCart(Long orderId, Long dishId, Integer quantity, Double price) {
        // 1. 检查订单是否存在且状态为0（未下单）
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != 0) {
            throw new BusinessException("订单已下单，无法修改购物车");
        }
        
        // 2. 检查菜品是否已在购物车中
        OrderItem existingItem = orderItemMapper.selectByOrderIdAndDishId(orderId, dishId, 1);
        
        if (existingItem != null) {
            // 如果已存在，更新数量
            return updateCartItemQuantity(existingItem.getId(), existingItem.getQuantity() + quantity);
        }
        
        // 3. 创建新的订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setDishId(dishId);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(BigDecimal.valueOf(price));
        orderItem.setSubtotal(BigDecimal.valueOf(price * quantity));
        orderItem.setOrderItemStatus(null); // 购物车状态，不设置菜品状态
        orderItem.setIsCart(1); // 购物车状态
        
        // 4. 插入数据库
        orderItemMapper.insert(orderItem);
        
        log.info("菜品添加到购物车成功: orderId={}, dishId={}, quantity={}, price={}", 
                orderId, dishId, quantity, price);
        
        return orderItem;
    }

    /**
     * 更新购物车中菜品的数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderItem updateCartItemQuantity(Long orderItemId, Integer quantity) {
        // 1. 检查订单项是否存在
        OrderItem orderItem = orderItemMapper.selectById(orderItemId);
        if (orderItem == null) {
            throw new BusinessException("购物车项不存在");
        }
        
        // 2. 检查订单状态
        Order order = orderMapper.selectById(orderItem.getOrderId());
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单已下单，无法修改购物车");
        }
        
        // 3. 检查是否为购物车状态
        if (orderItem.getIsCart() != 1) {
            throw new BusinessException("该菜品不在购物车中");
        }
        
        // 4. 更新数量和金额
        if (quantity <= 0) {
            // 如果数量为0或负数，从购物车中移除
            removeFromCart(orderItemId);
            return null;
        }
        
        orderItem.setQuantity(quantity);
        orderItem.setSubtotal(orderItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        
        // 5. 更新数据库
        orderItemMapper.update(orderItem);
        
        log.info("购物车菜品数量更新成功: orderItemId={}, newQuantity={}", orderItemId, quantity);
        
        return orderItem;
    }

    /**
     * 从购物车中移除菜品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFromCart(Long orderItemId) {
        // 1. 检查订单项是否存在
        OrderItem orderItem = orderItemMapper.selectById(orderItemId);
        if (orderItem == null) {
            throw new BusinessException("购物车项不存在");
        }
        
        // 2. 检查订单状态
        Order order = orderMapper.selectById(orderItem.getOrderId());
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单已下单，无法修改购物车");
        }
        
        // 3. 检查是否为购物车状态
        if (orderItem.getIsCart() != 1) {
            throw new BusinessException("该菜品不在购物车中");
        }
        
        // 4. 删除订单项
        orderItemMapper.deleteById(orderItemId);
        
        log.info("菜品从购物车移除成功: orderItemId={}, orderId={}, dishId={}", 
                orderItemId, orderItem.getOrderId(), orderItem.getDishId());
    }

    /**
     * 根据订单ID获取购物车中的菜品列表
     */
    @Override
    public List<OrderItem> getCartItems(Long orderId) {
        // 获取购物车中的所有菜品（is_cart = 1）
        return orderItemMapper.selectByOrderIdAndCartStatus(orderId, 1);
    }

    /**
     * 根据订单ID和菜品ID获取购物车中的菜品
     */
    @Override
    public OrderItem getCartItemByOrderIdAndDishId(Long orderId, Long dishId) {
        return orderItemMapper.selectByOrderIdAndDishId(orderId, dishId, 1);
    }

    /**
     * 清空购物车
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long orderId) {
        // 1. 检查订单状态
        Order order = orderMapper.selectById(orderId);
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单已下单，无法清空购物车");
        }
        
        // 2. 删除该订单的所有购物车项
        List<OrderItem> cartItems = getCartItems(orderId);
        for (OrderItem item : cartItems) {
            orderItemMapper.deleteById(item.getId());
        }
        
        log.info("购物车清空成功: orderId={}, clearedItems={}", orderId, cartItems.size());
    }

    /**
     * 获取购物车中的菜品数量
     */
    @Override
    public int getCartItemCount(Long orderId) {
        return orderItemMapper.countCartItemsByOrderId(orderId);
    }

    /**
     * 计算购物车总金额
     */
    @Override
    public Double calculateCartTotal(Long orderId) {
        List<OrderItem> cartItems = getCartItems(orderId);

        double total = 0.0;
        for (OrderItem item : cartItems) {
            total += item.getSubtotal().doubleValue();
        }
        
        return total;
    }
}
