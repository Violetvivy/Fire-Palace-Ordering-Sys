package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 * 对应表: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 用户类型，0普通，1会员，2服务员
     */
    private Integer role;
}
