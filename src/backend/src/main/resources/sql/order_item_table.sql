-- 表名: order_item
-- 说明: 订单明细表（订单中的菜品项）
CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    dish_id BIGINT NOT NULL COMMENT '菜品ID',
    quantity INT NOT NULL COMMENT '菜品数量',
    price DECIMAL(10,2) NOT NULL COMMENT '菜品单价',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计金额（quantity * price）',
    order_item_status TINYINT COMMENT '菜品状态：0制作中 1上菜中 2已上菜',
    INDEX idx_order_id (order_id),
    INDEX idx_dish_id (dish_id),
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_order_item_dish FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';
