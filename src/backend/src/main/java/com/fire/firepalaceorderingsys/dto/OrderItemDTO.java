package com.fire.firepalaceorderingsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单明细数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
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
}
