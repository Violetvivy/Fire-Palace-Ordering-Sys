package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品标签实体类
 * 对应表: dish_tag
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishTag {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;
}
