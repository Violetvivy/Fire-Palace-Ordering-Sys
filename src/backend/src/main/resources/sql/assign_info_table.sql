-- 表名: assign_info
-- 说明: 分配信息表（记录管理员分配服务员到包厢的信息）
CREATE TABLE IF NOT EXISTS assign_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键ID',
    admin_id BIGINT NOT NULL COMMENT '管理员ID',
    waiter_id BIGINT NOT NULL COMMENT '服务员ID',
    room_id BIGINT NOT NULL COMMENT '包厢ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted_at DATETIME DEFAULT NULL COMMENT '删除时间',
    UNIQUE INDEX uk_waiter_room_created (waiter_id, room_id, created_at),
    INDEX idx_admin_id (admin_id),
    INDEX idx_waiter_id (waiter_id),
    INDEX idx_room_id (room_id),
    INDEX idx_created_at (created_at),
    CONSTRAINT fk_assign_info_admin FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_assign_info_waiter FOREIGN KEY (waiter_id) REFERENCES waiter(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_assign_info_room FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分配信息表';
