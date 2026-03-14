package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Waiter;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 服务员Mapper接口
 */
@Mapper
public interface WaiterMapper {

    /**
     * 根据ID查询服务员
     */
    @Select("SELECT * FROM waiter WHERE id = #{id} AND deleted_at IS NULL")
    Waiter selectById(Long id);

    /**
     * 根据工号查询服务员
     */
    @Select("SELECT * FROM waiter WHERE work_no = #{workNo} AND deleted_at IS NULL")
    Waiter selectByWorkNo(String workNo);

    /**
     * 根据手机号查询服务员
     */
    @Select("SELECT * FROM waiter WHERE phone = #{phone} AND deleted_at IS NULL")
    Waiter selectByPhone(String phone);

    /**
     * 查询所有服务员
     */
    @Select("SELECT * FROM waiter WHERE deleted_at IS NULL ORDER BY created_at DESC")
    List<Waiter> selectAll();

    /**
     * 新增服务员
     */
    @Insert("INSERT INTO waiter (waitername, phone, work_no, created_at) VALUES (#{waitername}, #{phone}, #{workNo}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Waiter waiter);

    /**
     * 更新服务员信息
     */
    @Update("UPDATE waiter SET waitername = #{waitername}, phone = #{phone}, work_no = #{workNo} WHERE id = #{id} AND deleted_at IS NULL")
    int update(Waiter waiter);

    /**
     * 软删除服务员
     */
    @Update("UPDATE waiter SET deleted_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 根据姓名模糊查询服务员
     */
    @Select("SELECT * FROM waiter WHERE waitername LIKE CONCAT('%', #{name}, '%') AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Waiter> selectByName(String name);
}
