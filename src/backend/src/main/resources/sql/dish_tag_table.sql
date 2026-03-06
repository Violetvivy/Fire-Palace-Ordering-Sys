-- 表名: dish_tag
-- 说明: 菜品标签表
CREATE TABLE IF NOT EXISTS dish_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    UNIQUE INDEX uk_tag_name (tag_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品标签表';
