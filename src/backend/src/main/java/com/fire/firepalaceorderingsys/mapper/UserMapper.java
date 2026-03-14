package com.fire.firepalaceorderingsys.mapper;

import com.fire.firepalaceorderingsys.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 检查会员是否已存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username} AND phone = #{phone} AND deleted_at IS NULL")
    int checkUserExists(String username, String phone);

    /**
     * 插入会员
     */
    @Insert("INSERT INTO user (username, phone, role) VALUES (#{username}, #{phone}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 查询会员
     */
    @Select("SELECT * FROM user WHERE phone = #{phone} AND deleted_at IS NULL")
    User selectByPhone(String phone);

    /**
     * 删除会员
     */
    @Update("UPDATE user SET deleted_at = NOW() WHERE phone = #{phoen}")
    int deleteByPhone(String phone);
}
