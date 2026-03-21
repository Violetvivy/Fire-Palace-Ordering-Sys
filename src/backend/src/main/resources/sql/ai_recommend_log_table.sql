-- 表名: ai_recommend_log
-- 说明: AI推荐日志表
CREATE TABLE IF NOT EXISTS ai_recommend_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    people_count INT NOT NULL COMMENT '用餐人数',
    budget DECIMAL(10,2) NOT NULL COMMENT '预算金额',
    pre_tag JSON NOT NULL COMMENT '预选偏好标签(JSON格式)',
    recommend_result JSON COMMENT 'AI推荐结果（JSON格式）',
    actual_order_amount DECIMAL(10,2) COMMENT '实际订单金额',
    accept_rate TINYINT COMMENT '是否接受推荐',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_order_id (order_id),
    INDEX idx_created_at (created_at),
    CONSTRAINT fk_ai_recommend_log_user FOREIGN KEY (user_id) REFERENCES user_profile(user_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_ai_recommend_log_order FOREIGN KEY (order_id) REFERENCES 'order'(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI推荐日志表';
