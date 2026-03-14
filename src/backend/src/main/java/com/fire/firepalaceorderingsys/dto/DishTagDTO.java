package com.fire.firepalaceorderingsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品标签数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishTagDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;
}
