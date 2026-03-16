package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.dto.OrderDTO;
import com.fire.firepalaceorderingsys.pojo.Order;
import com.fire.firepalaceorderingsys.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    /**
     * 创建订单
     * @param roomName 包厢名
     * @param userId 用户ID
     * @return 订单VO
     */
    OrderVO createOrder(String roomName, Long userId);

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
}
