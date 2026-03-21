package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.dto.OrderDTO;
import com.fire.firepalaceorderingsys.pojo.Order;
import com.fire.firepalaceorderingsys.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    /**
     * 创建订单（进入包厢时创建，status=0）
     */
    OrderVO createOrder(String roomName, Long userId);

    /**
     * 下单（将购物车中的菜品提交，status从0改为1）
     */
    void submitOrder(Long orderId);

    /**
     * 根据ID获取订单
     */
    Order getOrderById(Long id);

    /**
     * 根据订单号获取订单
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 根据用户ID获取订单列表
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * 根据服务员ID获取订单列表
     */
    List<Order> getOrdersByWaiterId(Long waiterId);

    /**
     * 根据包厢ID获取订单列表
     */
    List<Order> getOrdersByRoomId(Long roomId);

    /**
     * 获取所有订单
     */
    List<Order> getAllOrders();

    /**
     * 根据状态获取订单列表
     */
    List<Order> getOrdersByStatus(Integer status);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(Long id, Integer status);

    /**
     * 更新订单信息（就餐人数和预算）
     */
    void updateOrderInfo(OrderDTO orderDTO);

    /**
     * 删除订单
     */
    void deleteOrder(Long id);

    /**
     * 餐前分析校验
     * 获取订单的购物车商品列表和AI推荐偏好，打包发送给AI校验服务
     * @param orderId 订单ID
     * @return AI校验结果
     */
    Object preMealAnalysis(Long orderId);
}
