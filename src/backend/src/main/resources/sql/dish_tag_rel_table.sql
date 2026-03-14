-- 表名: dish_tag_rel
-- 说明: 菜品标签关系表（多对多）
CREATE TABLE IF NOT EXISTS dish_tag_rel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    dish_id BIGINT NOT NULL COMMENT '菜品ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted_at DATETIME DEFAULT NUll COMMENT '删除时间',
    UNIQUE INDEX uk_dish_tag (dish_id, tag_id),
    INDEX idx_dish_id (dish_id),
    INDEX idx_tag_id (tag_id),
    CONSTRAINT fk_dish_tag_rel_dish FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_dish_tag_rel_tag FOREIGN KEY (tag_id) REFERENCES dish_tag(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品标签关系表';
