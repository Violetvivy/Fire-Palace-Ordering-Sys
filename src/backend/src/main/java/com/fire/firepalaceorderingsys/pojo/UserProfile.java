package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户基础偏好实体类
 * 对应表: user_profile
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 平均消费金额
     */
    private BigDecimal avgSpend;

    /**
     * 最喜爱的菜品分类
     */
    private String favoriteCategory;

    /**
     * 辣度偏好：0不辣，1微辣，2中辣，3重辣
     */
    private Integer spicyPreference;

    /**
     * 到店次数
     */
    private Integer visitCount;

    /**
     * 最后到店时间
     */
    private LocalDateTime lastVisitTime;
}
