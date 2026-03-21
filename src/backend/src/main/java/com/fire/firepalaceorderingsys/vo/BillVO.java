package com.fire.firepalaceorderingsys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单VO类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillVO {
    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 包厢名
     */
    private String roomName;

    /**
     * 服务员工号
     */
    private String waiterWorkNo;

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
     * 创建时间
     */
    private LocalDateTime createdAt;
}
