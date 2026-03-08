package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 检查会员是否已存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username} AND phone = #{phone}")
    int checkUserExists(String username, String phone);

    /**
     * 插入会员
     */
    @Insert("INSERT INTO user (username, phone, role) VALUES (#{username}, #{phone}, #{role})")
    void insert(User user);

    /**
     * 查询会员
     */
    @Select("SELECT * FROM user WHERE phone = #{account}")
    User selectByPhone(String phone);
}
