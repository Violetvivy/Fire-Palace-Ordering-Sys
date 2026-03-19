package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类Mapper接口
 */
@Mapper
public interface CategoryMapper {

    /**
     * 根据ID查询分类
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category selectById(Long id);

    /**
     * 查询所有分类，按排序序号升序
     */
    @Select("SELECT * FROM category ORDER BY sort_order ASC")
    List<Category> selectAll();

    /**
     * 根据父分类ID查询子分类
     */
    @Select("SELECT * FROM category WHERE parent_id = #{parentId} ORDER BY sort_order ASC")
    List<Category> selectByParentId(Long parentId);
}
