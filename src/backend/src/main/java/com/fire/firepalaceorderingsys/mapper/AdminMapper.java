package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    /**
     * 查询管理员
     */
    @Select("SELECT * FROM admin WHERE phone = #{phone} AND adminname = #{adminname} AND deleted_at IS NULL")
    Admin selectByPhone(String adminname, String phone);
}
