package com.fire.firepalaceorderingsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 包厢ID
     */
    private Long roomId;

    /**
     * 用餐人数
     */
    private Integer peopleCount;

    /**
     * 预算金额
     */
    private BigDecimal budget;

    /**
     * 实际总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态：0未支付，1已支付，2完成
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
