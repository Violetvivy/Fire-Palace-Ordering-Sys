create table user_profile
(
    id                   bigint auto_increment comment '主键ID'
        primary key,
    user_id              bigint                                   not null comment '用户ID',
    avg_spend            decimal(10, 2) default 0.00              null comment '平均消费金额',
    favorite_category    varchar(100)                             null comment '最喜爱的菜品分类',
    spicy_preference     tinyint        default 0                 null comment '辣度偏好：0不辣，1微辣，2中辣，3重辣',
    visit_count          int            default 0                 null comment '到店次数',
    last_visit_time      datetime                                 not null comment '最后到店时间',
    created_at           datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    deleted_at           datetime                                 null comment '删除时间',
    sweet_preference     tinyint        default 0                 null comment '甜度偏好：0无甜，1微甜，2中甜，3重甜',
    salty_preference     tinyint        default 0                 null comment '咸度偏好：0清淡，1适中，2偏咸',
    oil_preference       tinyint        default 0                 null comment '油脂偏好：0少油，1适中，2多油',
    allergy_ingredients  json                                     null comment '过敏食材（JSON格式）',
    dietary_restrictions json                                     null comment '忌口信息（JSON格式）',
    frequent_dishes      json                                     null comment '常点菜品（JSON格式，存储菜品ID数组）',
    constraint uk_user_id
        unique (user_id),
    constraint fk_user_profile_user
        foreign key (user_id) references user (id)
            on update cascade
)
    comment '用户基础偏好表' charset = utf8mb4;

create index idx_favorite_category
    on user_profile (favorite_category);

create index idx_last_visit_time
    on user_profile (last_visit_time);

create index idx_oil_preference
    on user_profile (oil_preference);

create index idx_salty_preference
    on user_profile (salty_preference);

create index idx_sweet_preference
    on user_profile (sweet_preference);

