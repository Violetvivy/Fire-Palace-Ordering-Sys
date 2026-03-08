package com.fire.firepalaceorderingsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类ID，0表示根分类
     */
    private Long parentId;

    /**
     * 排序序号，控制排序显示优先级
     */
    private Integer sortOrder;
}
