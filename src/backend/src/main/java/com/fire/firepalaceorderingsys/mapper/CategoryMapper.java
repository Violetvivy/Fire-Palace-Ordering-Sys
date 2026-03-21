package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 新增分类
     * @param category 要新增的分类对象
     * @return 受影响的行数（成功返回1，失败返回0）
     */
    @Insert("""
        INSERT INTO category (name, parent_id, sort_order)
        VALUES (#{name}, #{parentId}, #{sortOrder})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    /**
     * 根据id修改分类信息
     * @param category 要修改的分类对象（必须带id）
     * @return 受影响的行数
     */
    @Update("""
        UPDATE category
        SET name = #{name}, parent_id = #{parentId}, sort_order = #{sortOrder}
        WHERE id = #{id}
    """)
    int update(Category category);

    /**
     * 根据id删除分类
     * @param id 要删除的分类的主键id
     * @return 受影响的行数
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据id查询分类详情
     * @param id 要查询的分类id
     * @return 分类对象
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category selectById(Long id);

    /**
     * 查询所有分类列表（按排序号升序排列）
     * @return 分类列表
     */
    @Select("SELECT * FROM category ORDER BY sort_order ASC")
    List<Category> selectAll();

}