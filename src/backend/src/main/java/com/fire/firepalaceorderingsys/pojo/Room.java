package com.fire.firepalaceorderingsys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 包厢实体类
 * 对应表: room
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 容纳人数
     */
    private Integer capacity;

    /**
     * 最低消费
     */
    private BigDecimal minConsume;

    /**
     * 状态: 0空闲 1使用中
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}
