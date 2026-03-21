package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单明细实体类
 * 对应表: order_item
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 菜品ID
     */
    private Long dishId;

    /**
     * 菜品数量
     */
    private Integer quantity;

    /**
     * 菜品单价
     */
    private BigDecimal price;

    /**
     * 小计金额（quantity * price）
     */
    private BigDecimal subtotal;

    /**
     * 菜品状态：0制作中 1上菜中 2已上菜
     */
    private Integer orderItemStatus;

    /**
     * 是否为购物车状态：1-是（购物车中），0-否（已下单）
     */
    private Integer isCart;
}
