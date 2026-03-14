package com.fire.firepalaceorderingsys.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 包厢数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 房间名称
     */
    @NotBlank(message = "包厢名不能为空")
    private String roomName;

    /**
     * 容纳人数
     */
    @NotNull(message = "容纳人数不能为空")
    private Integer capacity;

    /**
     * 最低消费
     */
    @NotNull(message = "最低消费不能为空")
    private BigDecimal minConsume;

    /**
     * 状态: 0空闲 1使用中
     */
    @NotNull(message = "包厢状态不能为空")
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
