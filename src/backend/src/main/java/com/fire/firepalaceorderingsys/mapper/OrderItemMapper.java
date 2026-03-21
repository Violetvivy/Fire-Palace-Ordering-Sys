package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    /**
     * 插入订单明细
     */
    @Insert("INSERT INTO order_item (order_id, dish_id, quantity, price, subtotal, order_item_status, is_cart) " +
            "VALUES (#{orderId}, #{dishId}, #{quantity}, #{price}, #{subtotal}, #{orderItemStatus}, #{isCart})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrderItem orderItem);

    /**
     * 根据ID查询订单明细
     */
    @Select("SELECT * FROM order_item WHERE id = #{id}")
    OrderItem selectById(Long id);

    /**
     * 根据订单ID查询订单明细列表
     */
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> selectByOrderId(Long orderId);

    /**
     * 根据订单ID和购物车状态查询订单明细列表
     */
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId} AND is_cart = #{isCart}")
    List<OrderItem> selectByOrderIdAndCartStatus(@Param("orderId") Long orderId, @Param("isCart") Integer isCart);

    /**
     * 根据菜品ID查询订单明细列表
     */
    @Select("SELECT * FROM order_item WHERE dish_id = #{dishId}")
    List<OrderItem> selectByDishId(Long dishId);

    /**
     * 查询所有订单明细
     */
    @Select("SELECT * FROM order_item")
    List<OrderItem> selectAll();

    /**
     * 根据ID更新订单明细
     */
    @Update("<script>" +
            "UPDATE order_item" +
            "<set>" +
            "    <if test='orderId != null'>order_id = #{orderId},</if>" +
            "    <if test='dishId != null'>dish_id = #{dishId},</if>" +
            "    <if test='quantity != null'>quantity = #{quantity},</if>" +
            "    <if test='price != null'>price = #{price},</if>" +
            "    <if test='subtotal != null'>subtotal = #{subtotal},</if>" +
            "    <if test='orderItemStatus != null'>order_item_status = #{orderItemStatus},</if>" +
            "    <if test='isCart != null'>is_cart = #{isCart},</if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int update(OrderItem orderItem);

    /**
     * 根据ID删除订单明细
     */
    @Delete("DELETE FROM order_item WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据订单ID删除订单明细
     */
    @Delete("DELETE FROM order_item WHERE order_id = #{orderId}")
    int deleteByOrderId(Long orderId);

    /**
     * 根据订单ID和菜品ID查询订单明细
     */
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId} AND dish_id = #{dishId} AND is_cart = #{isCart}")
    OrderItem selectByOrderIdAndDishId(@Param("orderId") Long orderId, @Param("dishId") Long dishId, @Param("isCart") Integer isCart);

    /**
     * 更新购物车状态
     */
    @Update("UPDATE order_item SET is_cart = #{isCart} WHERE order_id = #{orderId}")
    int updateCartStatusByOrderId(@Param("orderId") Long orderId, @Param("isCart") Integer isCart);

    /**
     * 根据订单ID统计购物车中的菜品数量
     */
    @Select("SELECT COUNT(*) FROM order_item WHERE order_id = #{orderId} AND is_cart = 1")
    int countCartItemsByOrderId(Long orderId);

    /**
     * 根据订单ID获取购物车中的菜品ID列表
     */
    @Select("SELECT dish_id FROM order_item WHERE order_id = #{orderId} AND is_cart = 1")
    List<Long> selectCartDishIdsByOrderId(Long orderId);
}
