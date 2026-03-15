package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Room;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoomMapper {

    /**
     * 插入包厢
     */
    @Insert("INSERT INTO room (room_name, capacity, min_consume, status) " +
            "VALUES (#{roomName}, #{capacity}, #{minConsume}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Room room);

    /**
     * 根据ID删除包厢（软删除）
     */
    @Update("UPDATE room SET deleted_at = NOW() WHERE id = #{id} AND deleted_at IS NULL")
    int deleteById(Long id);

    /**
     * 根据ID更新包厢信息
     */
    @Update("UPDATE room SET room_name = #{roomName}, capacity = #{capacity}, " +
            "min_consume = #{minConsume}, status = #{status} " +
            "WHERE id = #{id} AND deleted_at IS NULL")
    int update(Room room);

    /**
     * 根据ID查询包厢
     */
    @Select("SELECT * FROM room WHERE id = #{id} AND deleted_at IS NULL")
    Room selectById(Long id);

    /**
     * 查询所有包厢（未删除的）
     */
    @Select("SELECT * FROM room WHERE deleted_at IS NULL ORDER BY created_at DESC")
    List<Room> selectAll();

    /**
     * 根据状态查询包厢
     */
    @Select("SELECT * FROM room WHERE status = #{status} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Room> selectByStatus(Integer status);

    /**
     * 检查包厢名称是否已存在
     */
    @Select("SELECT COUNT(*) FROM room WHERE room_name = #{roomName} AND deleted_at IS NULL")
    int checkRoomNameExists(String roomName);

    /**
     * 根据包厢名称查询包厢
     */
    @Select("SELECT * FROM room WHERE room_name = #{roomName} AND deleted_at IS NULL")
    Room selectByRoomName(String roomName);

    /**
     * 更新包厢状态
     */
    @Update("UPDATE room SET status = #{status} WHERE id = #{id} AND deleted_at IS NULL")
    int updateStatus(Long id, Integer status);
}
