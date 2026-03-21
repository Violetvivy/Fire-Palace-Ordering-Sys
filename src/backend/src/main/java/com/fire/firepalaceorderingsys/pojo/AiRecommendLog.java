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
     * 订单ID
     */
    private Long orderId;

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
     * 是否接受推荐：0-不接受，1-接受
     */
    private Integer acceptRate;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
