package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.UserProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserProfileMapper {

    /**
     * 插入用户偏好信息
     */
    @Insert("INSERT INTO user_profile (user_id, avg_spend, favorite_category, spicy_preference, last_visit_time, created_at) " +
            "VALUES (#{userId}, #{avgSpend}, #{favoriteCategory}, #{spicyPreference}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(UserProfile userProfile);

    /**
     * 根据ID查询用户偏好信息
     */
    @Select("SELECT * FROM user_profile WHERE id = #{id} AND deleted_at IS NULL")
    UserProfile selectById(Long id);

    /**
     * 根据用户ID查询用户偏好信息
     */
    @Select("SELECT * FROM user_profile WHERE user_id = #{userId} AND deleted_at IS NULL")
    UserProfile selectByUserId(Long userId);

    /**
     * 查询所有用户偏好信息
     */
    @Select("SELECT * FROM user_profile WHERE deleted_at IS NULL ORDER BY last_visit_time DESC")
    List<UserProfile> selectAll();

    /**
     * 根据ID更新用户偏好信息
     */
    @Update("<script>" +
            "UPDATE user_profile " +
            "<set>" +
            "    <if test='userId != null'>user_id = #{userId},</if>" +
            "    <if test='avgSpend != null'>avg_spend = #{avgSpend},</if>" +
            "    <if test='favoriteCategory != null'>favorite_category = #{favoriteCategory},</if>" +
            "    <if test='spicyPreference != null'>spicy_preference = #{spicyPreference},</if>" +
            "    <if test='visitCount != null'>visit_count = #{visitCount},</if>" +
            "    <if test='lastVisitTime != null'>last_visit_time = #{lastVisitTime},</if>" +
            "</set>" +
            "WHERE id = #{id} AND deleted_at IS NULL" +
            "</script>")
    int update(UserProfile userProfile);

    /**
     * 根据ID删除用户偏好信息（软删除）
     */
    @Update("UPDATE user_profile SET deleted_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据用户ID删除用户偏好信息（软删除）
     */
    @Update("UPDATE user_profile SET deleted_at = NOW() WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);

    /**
     * 检查用户偏好信息是否存在
     */
    @Select("SELECT COUNT(*) FROM user_profile WHERE user_id = #{userId} AND deleted_at IS NULL")
    int checkExistsByUserId(Long userId);

    /**
     * 根据辣度偏好查询用户偏好信息
     */
    @Select("SELECT * FROM user_profile WHERE spicy_preference = #{spicyPreference} AND deleted_at IS NULL ORDER BY last_visit_time DESC")
    List<UserProfile> selectBySpicyPreference(Integer spicyPreference);

    /**
     * 根据菜品分类查询用户偏好信息
     */
    @Select("SELECT * FROM user_profile WHERE favorite_category = #{favoriteCategory} AND deleted_at IS NULL ORDER BY last_visit_time DESC")
    List<UserProfile> selectByFavoriteCategory(String favoriteCategory);

    /**
     * 更新用户到店次数和最后到店时间
     */
    @Update("UPDATE user_profile SET visit_count = visit_count + 1, last_visit_time = NOW() WHERE user_id = #{userId} AND deleted_at IS NULL")
    int updateVisitInfo(Long userId);

    /**
     * 更新用户平均消费金额
     */
    @Update("UPDATE user_profile SET avg_spend = #{avgSpend} WHERE user_id = #{userId} AND deleted_at IS NULL")
    int updateAvgSpend(@Param("userId") Long userId, @Param("avgSpend") java.math.BigDecimal avgSpend);
}
