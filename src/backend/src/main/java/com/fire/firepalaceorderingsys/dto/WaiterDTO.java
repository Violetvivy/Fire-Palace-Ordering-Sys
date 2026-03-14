package com.fire.firepalaceorderingsys.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 服务员数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterDTO {
    /**
     * 服务员ID
     */
    private Long id;

    /**
     * 服务员姓名
     */
    @NotBlank(message = "服务员姓名不能为空")
    private String waitername;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号必须是11位号码")
    private String phone;

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    private String workNo;
}
