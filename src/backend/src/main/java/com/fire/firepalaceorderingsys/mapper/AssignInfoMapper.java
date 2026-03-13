package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.AssignInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignInfoMapper {

    /**
     * 插入分配信息
     */
    @Insert("INSERT INTO assign_info (admin_id, waiter_id, room_id) VALUES (#{adminId}, #{waiterId}, #{roomId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AssignInfo assignInfo);

    /**
     * 根据ID查询分配信息
     */
    @Select("SELECT * FROM assign_info WHERE id = #{id} AND deleted_at IS NULL")
    AssignInfo selectById(Long id);

    /**
     * 查询所有未删除的分配信息
     */
    @Select("SELECT * FROM assign_info WHERE deleted_at IS NULL ORDER BY created_at DESC")
    List<AssignInfo> selectAll();

    /**
     * 根据服务员ID查询分配信息
     */
    @Select("SELECT * FROM assign_info WHERE waiter_id = #{waiterId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<AssignInfo> selectByWaiterId(Long waiterId);

    /**
     * 根据包厢ID查询分配信息
     */
    @Select("SELECT * FROM assign_info WHERE room_id = #{roomId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<AssignInfo> selectByRoomId(Long roomId);

    /**
     * 根据管理员ID查询分配信息
     */
    @Select("SELECT * FROM assign_info WHERE admin_id = #{adminId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<AssignInfo> selectByAdminId(Long adminId);

    /**
     * 逻辑删除分配信息
     */
    @Update("UPDATE assign_info SET deleted_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 检查是否存在相同的分配记录（同一服务员、同一包厢、同一时间）
     */
    @Select("SELECT COUNT(*) FROM assign_info WHERE waiter_id = #{waiterId} AND room_id = #{roomId} AND DATE(created_at) = DATE(#{createdAt}) AND deleted_at IS NULL")
    int checkDuplicateAssign(Long waiterId, Long roomId, java.time.LocalDateTime createdAt);

    /**
     * 根据日期查询当天的所有分配信息
     */
    @Select("SELECT * FROM assign_info WHERE DATE(created_at) = DATE(#{date}) AND deleted_at IS NULL ORDER BY created_at DESC")
    List<AssignInfo> selectByDate(java.time.LocalDate date);
}
