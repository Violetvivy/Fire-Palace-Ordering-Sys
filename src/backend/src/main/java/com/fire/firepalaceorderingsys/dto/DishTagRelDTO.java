package com.fire.firepalaceorderingsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品标签关系数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishTagRelDTO {
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
}
