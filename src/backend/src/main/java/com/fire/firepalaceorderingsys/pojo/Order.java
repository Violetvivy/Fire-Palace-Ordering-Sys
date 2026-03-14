package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * 对应表: `order`
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
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
     * 服务员ID
     */
    private Long waiterId;

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
     * 订单状态：0已下单，1已接单，2已完成
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}
