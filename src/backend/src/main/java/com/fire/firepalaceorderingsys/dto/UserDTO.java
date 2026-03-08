package com.fire.firepalaceorderingsys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

/**
 * 用户数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    //手机号格式验证
    @Pattern(regexp = "^1\\d{10}$", message = "手机号必须是11位号码")
    private String phone;

    /**
     * 用户类型: 0普通，1会员，2服务员
     */
    @NotNull(message = "角色不能为空")
    private Integer role;
}
