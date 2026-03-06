-- 表名: user_profile
-- 说明: 用户基础偏好表（存储用户偏好和统计信息）
CREATE TABLE IF NOT EXISTS user_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    avg_spend DECIMAL(10,2) DEFAULT 0 COMMENT '平均消费金额',
    favorite_category VARCHAR(100) NOT NULL COMMENT '最喜爱的菜品分类',
    spicy_preference TINYINT DEFAULT 0 COMMENT '辣度偏好：0不辣，1微辣，2中辣，3重辣',
    visit_count INT DEFAULT 0 COMMENT '到店次数',
    last_visit_time DATETIME NOT NULL COMMENT '最后到店时间',
    UNIQUE INDEX uk_user_id (user_id),
    INDEX idx_favorite_category (favorite_category),
    INDEX idx_last_visit_time (last_visit_time),
    CONSTRAINT fk_user_profile_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础偏好表';
