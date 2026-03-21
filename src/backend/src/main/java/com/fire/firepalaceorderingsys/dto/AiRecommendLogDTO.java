package com.fire.firepalaceorderingsys.dto;

import com.fire.firepalaceorderingsys.annotation.ValidJson;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * AI推荐日志数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiRecommendLogDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
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
    @ValidJson
    private String preTag;

    /**
     * AI推荐结果（JSON格式）
     */
    @ValidJson
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
