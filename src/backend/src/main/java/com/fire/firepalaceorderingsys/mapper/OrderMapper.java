package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     */
    @Insert("INSERT INTO `order` (order_no, user_id, room_id, waiter_id, people_count, budget, total_amount, status) " +
            "VALUES (#{orderNo}, #{userId}, #{roomId}, #{waiterId}, #{peopleCount}, #{budget}, #{totalAmount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    /**
     * 根据ID查询订单
     */
    @Select("SELECT * FROM `order` WHERE id = #{id} AND deleted_at IS NULL")
    Order selectById(Long id);

    /**
     * 根据订单号查询订单
     */
    @Select("SELECT * FROM `order` WHERE order_no = #{orderNo} AND deleted_at IS NULL")
    Order selectByOrderNo(String orderNo);

    /**
     * 根据用户ID查询订单
     */
    @Select("SELECT * FROM `order` WHERE user_id = #{userId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Order> selectByUserId(Long userId);

    /**
     * 根据服务员ID查询订单
     */
    @Select("SELECT * FROM `order` WHERE waiter_id = #{waiterId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Order> selectByWaiterId(Long waiterId);

    /**
     * 根据包厢ID查询订单
     */
    @Select("SELECT * FROM `order` WHERE room_id = #{roomId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Order> selectByRoomId(Long roomId);

    /**
     * 查询所有订单（未删除的）
     */
    @Select("SELECT * FROM `order` WHERE deleted_at IS NULL ORDER BY created_at DESC")
    List<Order> selectAll();

    /**
     * 根据状态查询订单
     */
    @Select("SELECT * FROM `order` WHERE status = #{status} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Order> selectByStatus(Integer status);

    /**
     * 更新订单状态
     */
    @Update("UPDATE `order` SET status = #{status} WHERE id = #{id} AND deleted_at IS NULL")
    int updateStatus(Long id, Integer status);

    /**
     * 更新订单总金额
     */
    @Update("UPDATE `order` SET total_amount = #{totalAmount} WHERE id = #{id} AND deleted_at IS NULL")
    int updateTotalAmount(Long id, java.math.BigDecimal totalAmount);

    /**
     * 根据订单号更新订单信息
     */
    @Update("UPDATE `order` SET user_id = #{userId}, room_id = #{roomId}, " +
            "waiter_id = #{waiterId}, people_count = #{peopleCount}, budget = #{budget}, " +
            "total_amount = #{totalAmount}, status = #{status} " +
            "WHERE order_no = #{orderNo} AND deleted_at IS NULL")
    int updateByOrderNo(com.fire.firepalaceorderingsys.pojo.Order order);

    /**
     * 逻辑删除订单
     */
    @Update("UPDATE `order` SET deleted_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
}
