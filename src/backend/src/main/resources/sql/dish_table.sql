-- 表名: dish
-- 说明: 菜品表
CREATE TABLE IF NOT EXISTS dish (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '菜品名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    price DECIMAL(10,2) NOT NULL COMMENT '销售价格',
    cost_price DECIMAL(10,2) DEFAULT 0 COMMENT '成本价格',
    spicy_level TINYINT DEFAULT 0 COMMENT '辣度等级：0不辣，1微辣，2中辣，3重辣',
    is_signature TINYINT DEFAULT 0 COMMENT '是否招牌菜：0否，1是',
    image_url VARCHAR(255) NOT NULL COMMENT '菜品图片URL',
    video_url VARCHAR(255) COMMENT '菜品视频URL',
    description TEXT NOT NULL COMMENT '菜品描述',
    cultural_story VARCHAR(255) not null comment '文化故事',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1上架，0下架',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted_at DATETIME DEFAULT NUll COMMENT '删除时间',
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_is_signature (is_signature),
    INDEX idx_created_at (created_at),
    CONSTRAINT fk_dish_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品表';
