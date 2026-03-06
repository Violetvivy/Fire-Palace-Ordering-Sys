-- 表名: category
-- 说明: 分类表（支持多级分类）
CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT NOT NULL COMMENT '父分类ID，0表示根分类',
    sort_order INT DEFAULT 0 COMMENT '排序序号，控制排序显示优先级',
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';
