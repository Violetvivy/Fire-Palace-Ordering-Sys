-- 表名: `order`
-- 说明: 订单表
CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_no VARCHAR(50) NOT NULL COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    room_id BIGINT NOT NULL COMMENT '包厢ID',
    people_count INT NOT NULL COMMENT '用餐人数',
    budget DECIMAL(10,2) NOT NULL COMMENT '预算金额',
    total_amount DECIMAL(10,2) DEFAULT 0 COMMENT '实际总金额',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态：0未下单，1已下单，2已完成',
    waiter_id BIGINT NOT NULL COMMENT '服务员ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted_at DATETIME DEFAULT NUll COMMENT '删除时间',
    UNIQUE INDEX uk_order_no (order_no),
    INDEX idx_user_id (user_id),
    INDEX idx_room_id (room_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at),
    CONSTRAINT fk_order_room FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
