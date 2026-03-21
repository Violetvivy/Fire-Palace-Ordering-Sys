package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.pojo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {

    /**
     * 添加菜品到购物车
     * @param orderId 订单ID
     * @param dishId 菜品ID
     * @param quantity 数量
     * @param price 单价
     * @return 创建的订单项
     */
    OrderItem addToCart(Long orderId, Long dishId, Integer quantity, Double price);

    /**
     * 更新购物车中菜品的数量
     * @param orderItemId 订单项ID
     * @param quantity 新的数量
     * @return 更新后的订单项
     */
    OrderItem updateCartItemQuantity(Long orderItemId, Integer quantity);

    /**
     * 从购物车中移除菜品
     * @param orderItemId 订单项ID
     */
    void removeFromCart(Long orderItemId);

    /**
     * 根据订单ID获取购物车中的菜品列表
     * @param orderId 订单ID
     * @return 购物车中的菜品列表
     */
    List<OrderItem> getCartItems(Long orderId);

    /**
     * 根据订单ID和菜品ID获取购物车中的菜品
     * @param orderId 订单ID
     * @param dishId 菜品ID
     * @return 订单项
     */
    OrderItem getCartItemByOrderIdAndDishId(Long orderId, Long dishId);

    /**
     * 清空购物车
     * @param orderId 订单ID
     */
    void clearCart(Long orderId);

    /**
     * 获取购物车中的菜品数量
     * @param orderId 订单ID
     * @return 菜品数量
     */
    int getCartItemCount(Long orderId);

    /**
     * 计算购物车总金额
     * @param orderId 订单ID
     * @return 总金额
     */
    Double calculateCartTotal(Long orderId);
}
