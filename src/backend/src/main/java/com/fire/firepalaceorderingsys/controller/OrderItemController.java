package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.pojo.OrderItem;
import com.fire.firepalaceorderingsys.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单项控制器（购物车功能）
 */
@RestController
@RequestMapping("/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    /**
     * 添加菜品到购物车
     */
    @PostMapping("/cart/add")
    public Result addToCart(@RequestParam Long orderId, 
                           @RequestParam Long dishId,
                           @RequestParam Integer quantity,
                           @RequestParam Double price) {
        try {
            OrderItem orderItem = orderItemService.addToCart(orderId, dishId, quantity, price);
            return Result.success(orderItem);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新购物车中菜品的数量
     */
    @PutMapping("/cart/update-quantity")
    public Result updateCartItemQuantity(@RequestParam Long orderItemId,
                                        @RequestParam Integer quantity) {
        try {
            OrderItem orderItem = orderItemService.updateCartItemQuantity(orderItemId, quantity);
            if (orderItem == null) {
                return Result.success("菜品已从购物车移除");
            }
            return Result.success(orderItem);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 从购物车中移除菜品
     */
    @DeleteMapping("/cart/remove/{orderItemId}")
    public Result removeFromCart(@PathVariable Long orderItemId) {
        try {
            orderItemService.removeFromCart(orderItemId);
            return Result.success("移除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取购物车中的菜品列表
     */
    @GetMapping("/cart/{orderId}")
    public Result getCartItems(@PathVariable Long orderId) {
        try {
            List<OrderItem> cartItems = orderItemService.getCartItems(orderId);
            return Result.success(cartItems);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/cart/clear/{orderId}")
    public Result clearCart(@PathVariable Long orderId) {
        try {
            orderItemService.clearCart(orderId);
            return Result.success("购物车已清空");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取购物车中的菜品数量
     */
    @GetMapping("/cart/count/{orderId}")
    public Result getCartItemCount(@PathVariable Long orderId) {
        try {
            int count = orderItemService.getCartItemCount(orderId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 计算购物车总金额
     */
    @GetMapping("/cart/total/{orderId}")
    public Result calculateCartTotal(@PathVariable Long orderId) {
        try {
            Double total = orderItemService.calculateCartTotal(orderId);
            return Result.success(total);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 菜品跟踪接口，直接返回成功
     */
    @PostMapping("/track")
    public Result trackDish(@RequestParam String orderNo, @RequestParam Long dishId) {
        try {
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
