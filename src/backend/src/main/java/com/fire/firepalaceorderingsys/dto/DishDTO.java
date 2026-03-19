package com.fire.firepalaceorderingsys.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {
    /**
     * 主键ID（更新时必填）
     */
    private Long id;

    /**
     * 菜品名称
     */
    @NotBlank(message = "菜品名称不能为空")
    private String name;

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    /**
     * 销售价格
     */
    @NotNull(message = "销售价格不能为空")
    private BigDecimal price;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 辣度等级：0不辣，1微辣，2中辣，3重辣
     */
    private Integer spicyLevel;

    /**
     * 是否招牌菜：0否，1是
     */
    private Integer isSignature;

    /**
     * 菜品图片URL
     */
    @NotBlank(message = "菜品图片不能为空")
    private String imageUrl;

    /**
     * 菜品视频URL
     */
    private String videoUrl;

    /**
     * 菜品描述
     */
    @NotBlank(message = "菜品描述不能为空")
    private String description;

    /**
     * 文化故事
     */
    private String culturalStory;

    /**
     * 状态：1上架，0下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
