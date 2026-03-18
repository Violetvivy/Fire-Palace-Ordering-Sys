package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.AiRecommendLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AiRecommendLogMapper {

    /**
     * 插入AI推荐日志
     */
    @Insert("INSERT INTO ai_recommend_log (user_id, order_id, people_count, budget, pre_tag, recommend_result, actual_order_amount, accept_rate, created_at) " +
            "VALUES (#{userId}, #{orderId}, #{peopleCount}, #{budget}, #{preTag}, #{recommendResult}, #{actualOrderAmount}, #{acceptRate}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AiRecommendLog aiRecommendLog);

    /**
     * 根据ID查询AI推荐日志
     */
    @Select("SELECT * FROM ai_recommend_log WHERE id = #{id}")
    AiRecommendLog selectById(Long id);

    /**
     * 根据用户ID查询AI推荐日志列表
     */
    @Select("SELECT * FROM ai_recommend_log WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<AiRecommendLog> selectByUserId(Long userId);

    /**
     * 根据订单ID查询AI推荐日志
     */
    @Select("SELECT * FROM ai_recommend_log WHERE order_id = #{orderId}")
    AiRecommendLog selectByOrderId(Long orderId);

    /**
     * 查询所有AI推荐日志
     */
    @Select("SELECT * FROM ai_recommend_log ORDER BY created_at DESC")
    List<AiRecommendLog> selectAll();

    /**
     * 根据ID更新AI推荐日志
     */
    @Update("<script>" +
            "UPDATE ai_recommend_log " +
            "<set>" +
            "    <if test='userId != null'>user_id = #{userId},</if>" +
            "    <if test='orderId != null'>order_id = #{orderId},</if>" +
            "    <if test='peopleCount != null'>people_count = #{peopleCount},</if>" +
            "    <if test='budget != null'>budget = #{budget},</if>" +
            "    <if test='preTag != null'>pre_tag = #{preTag},</if>" +
            "    <if test='recommendResult != null'>recommend_result = #{recommendResult},</if>" +
            "    <if test='actualOrderAmount != null'>actual_order_amount = #{actualOrderAmount},</if>" +
            "    <if test='acceptRate != null'>accept_rate = #{acceptRate},</if>" +
            "    <if test='createdAt != null'>created_at = #{createdAt},</if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int update(AiRecommendLog aiRecommendLog);

    /**
     * 根据ID删除AI推荐日志
     */
    @Delete("DELETE FROM ai_recommend_log WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据用户ID删除AI推荐日志
     */
    @Delete("DELETE FROM ai_recommend_log WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);

    /**
     * 根据订单ID删除AI推荐日志
     */
    @Delete("DELETE FROM ai_recommend_log WHERE order_id = #{orderId}")
    int deleteByOrderId(Long orderId);
}
