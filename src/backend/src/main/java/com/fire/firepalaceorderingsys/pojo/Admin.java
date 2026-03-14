package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理员实体类
 * 对应表: admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 管理员姓名
     */
    private String adminname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}
