package com.fire.firepalaceorderingsys.pojo;

import com.fire.firepalaceorderingsys.annotation.ValidJson;
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
     * 甜度偏好：0无甜，1微甜，2中甜，3重甜
     */
    private Integer sweetPreference;

    /**
     * 咸度偏好：0清淡，1适中，2偏咸
     */
    private Integer saltyPreference;

    /**
     * 油脂偏好：0少油，1适中，2多油
     */
    private Integer oilPreference;

    /**
     * 过敏食材（JSON格式）
     */
    @ValidJson
    private String allergyIngredients;

    /**
     * 忌口信息（JSON格式）
     */
    @ValidJson
    private String dietaryRestrictions;

    /**
     * 常点菜品（JSON格式，存储菜品ID数组）
     */
    @ValidJson
    private String frequentDishes;

    /**
     * 到店次数
     */
    private Integer visitCount;

    /**
     * 最后到店时间
     */
    private LocalDateTime lastVisitTime;

    /**
     * 用户籍贯
     */
    private String nativePlace;
}
