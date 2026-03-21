package com.fire.firepalaceorderingsys.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "分类名不能为空")
    private String name;

    /**
     * 父分类ID，0表示根分类
     */
    @NotNull(message = "父类名不能为空")
    private Long parentId;

    /**
     * 排序序号，控制排序显示优先级
     */
    @NotNull(message = "排序优先级不能为空")
    private Integer sortOrder;
}
