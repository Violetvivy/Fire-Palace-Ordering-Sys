package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分配信息实体类
 * 对应表: assign_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignInfo {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     * 服务员ID
     */
    private Long waiterId;

    /**
     * 包厢ID
     */
    private Long roomId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}
