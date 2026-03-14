package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 菜品标签关系实体类
 * 对应表: dish_tag_rel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishTagRel {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 菜品ID
     */
    private Long dishId;

    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}
