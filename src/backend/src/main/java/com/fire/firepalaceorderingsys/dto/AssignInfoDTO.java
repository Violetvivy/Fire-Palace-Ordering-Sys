package com.fire.firepalaceorderingsys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分配信息数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignInfoDTO {
    /**
     * 管理员ID
     */
    @NotNull(message = "管理员ID不能为空")
    private Long adminId;

    /**
     * 服务员ID
     */
    @NotNull(message = "服务员ID不能为空")
    private Long waiterId;

    /**
     * 包厢ID
     */
    @NotNull(message = "包厢ID不能为空")
    private Long roomId;
}
