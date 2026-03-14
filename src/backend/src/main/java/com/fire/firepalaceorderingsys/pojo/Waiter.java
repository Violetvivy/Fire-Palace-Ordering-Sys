package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 服务员实体类
 * 对应表: waiter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Waiter {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 服务员姓名
     */
    private String waitername;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 工号
     */
    private String workNo;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}
