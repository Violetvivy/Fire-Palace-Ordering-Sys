package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * AI推荐日志实体类
 * 对应表: ai_recommend_log
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiRecommendLog {
    /**
     * 主键ID
     */
    private Long id;

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
     * 预选偏好标签(JSON格式)
     */
    private String preTag;

    /**
     * AI推荐结果（JSON格式）
     */
    private String recommendResult;

    /**
     * 实际订单金额
     */
    private BigDecimal actualOrderAmount;

    /**
     * 推荐接受率
     */
    private Float acceptRate;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
