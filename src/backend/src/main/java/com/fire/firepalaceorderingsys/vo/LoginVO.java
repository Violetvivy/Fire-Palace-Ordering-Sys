package com.fire.firepalaceorderingsys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录响应VO
 */
@Data
@AllArgsConstructor
public class LoginVO {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * JWT Token
     */
    private String token;
}