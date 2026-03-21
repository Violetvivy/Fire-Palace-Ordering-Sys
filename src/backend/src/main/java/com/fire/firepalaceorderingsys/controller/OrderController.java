package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.OrderDTO;
import com.fire.firepalaceorderingsys.pojo.Order;
import com.fire.firepalaceorderingsys.service.OrderService;
import com.fire.firepalaceorderingsys.vo.OrderVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param roomName 包厢名
     * @param userId 用户ID
     */
    @PostMapping("/create")
    public Result createOrder(@RequestParam String roomName, @RequestParam Long userId) {
        try {
            OrderVO orderVO = orderService.createOrder(roomName, userId);
            return Result.success(orderVO);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID查询订单
     */
    @GetMapping("/select/{id}")
    public Result getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return Result.error("订单不存在");
            }
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据订单号查询订单
     */
    @GetMapping("/orderNo/{orderNo}")
    public Result getOrderByOrderNo(@PathVariable String orderNo) {
        try {
            Order order = orderService.getOrderByOrderNo(orderNo);
            if (order == null) {
                return Result.error("订单不存在");
            }
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据用户ID查询订单列表
     */
    @GetMapping("/user/{userId}")
    public Result getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据服务员ID查询订单列表
     */
    @GetMapping("/waiter/{waiterId}")
    public Result getOrdersByWaiterId(@PathVariable Long waiterId) {
        try {
            List<Order> orders = orderService.getOrdersByWaiterId(waiterId);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据包厢ID查询订单列表
     */
    @GetMapping("/room/{roomId}")
    public Result getOrdersByRoomId(@PathVariable Long roomId) {
        try {
            List<Order> orders = orderService.getOrdersByRoomId(roomId);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询所有订单
     */
    @GetMapping("/all")
    public Result getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据状态查询订单列表
     */
    @GetMapping("/status/{status}")
    public Result getOrdersByStatus(@PathVariable Integer status) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新订单状态
     */
    @PutMapping("/update/status")
    public Result updateOrderStatus(@RequestParam Long id, @RequestParam Integer status) {
        try {
            orderService.updateOrderStatus(id, status);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新订单信息（主要更新就餐人数和预算）
     */
    @PutMapping("/update")
    public Result updateOrderInfo(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            orderService.updateOrderInfo(orderDTO);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 下单（将购物车中的菜品提交）
     */
    @PostMapping("/submit/{orderId}")
    public Result submitOrder(@PathVariable Long orderId) {
        try {
            orderService.submitOrder(orderId);
            return Result.success("下单成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
