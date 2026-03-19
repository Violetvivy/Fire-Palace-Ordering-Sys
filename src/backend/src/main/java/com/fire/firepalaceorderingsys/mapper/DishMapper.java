package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 菜品Mapper接口
 */
@Mapper
public interface DishMapper {

    /**
     * 根据ID查询菜品
     */
    @Select("SELECT * FROM dish WHERE id = #{id} AND deleted_at IS NULL")
    Dish selectById(Long id);

    /**
     * 查询所有菜品
     */
    @Select("SELECT * FROM dish WHERE deleted_at IS NULL ORDER BY created_at DESC")
    List<Dish> selectAll();

    /**
     * 根据状态查询菜品
     */
    @Select("SELECT * FROM dish WHERE status = #{status} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Dish> selectByStatus(Integer status);

    /**
     * 根据分类ID查询菜品
     */
    @Select("SELECT * FROM dish WHERE category_id = #{categoryId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Dish> selectByCategory(Long categoryId);

    /**
     * 根据名称模糊查询菜品
     */
    @Select("SELECT * FROM dish WHERE name LIKE CONCAT('%', #{name}, '%') AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Dish> selectByName(String name);

    /**
     * 新增菜品
     */
    @Insert("INSERT INTO dish (name, category_id, price, cost_price, spicy_level, is_signature, " +
            "image_url, video_url, description, cultural_story, status, created_at) " +
            "VALUES (#{name}, #{categoryId}, #{price}, #{costPrice}, #{spicyLevel}, #{isSignature}, " +
            "#{imageUrl}, #{videoUrl}, #{description}, #{culturalStory}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Dish dish);

    /**
     * 动态更新菜品信息
     */
    @Update("<script>" +
            "UPDATE dish " +
            "<set>" +
            "  <if test='name != null and name != \"\"'>name = #{name},</if>" +
            "  <if test='categoryId != null'>category_id = #{categoryId},</if>" +
            "  <if test='price != null'>price = #{price},</if>" +
            "  <if test='costPrice != null'>cost_price = #{costPrice},</if>" +
            "  <if test='spicyLevel != null'>spicy_level = #{spicyLevel},</if>" +
            "  <if test='isSignature != null'>is_signature = #{isSignature},</if>" +
            "  <if test='imageUrl != null and imageUrl != \"\"'>image_url = #{imageUrl},</if>" +
            "  <if test='videoUrl != null'>video_url = #{videoUrl},</if>" +
            "  <if test='description != null and description != \"\"'>description = #{description},</if>" +
            "  <if test='culturalStory != null'>cultural_story = #{culturalStory},</if>" +
            "  <if test='status != null'>status = #{status},</if>" +
            "</set>" +
            "WHERE id = #{id} AND deleted_at IS NULL" +
            "</script>")
    int update(Dish dish);

    /**
     * 更新菜品上架/下架状态
     */
    @Update("UPDATE dish SET status = #{status} WHERE id = #{id} AND deleted_at IS NULL")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 软删除菜品
     */
    @Update("UPDATE dish SET deleted_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
}
