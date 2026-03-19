-- ============================================================
-- 初始化数据：分类 + 菜品（来源自前端 index.tsx 静态数据）
-- 使用显式 ID 避免 RDS auto_increment_increment=2 导致的 ID 不连续问题
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE dish;
TRUNCATE TABLE category;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 1. 插入分类数据（显式指定 ID）
-- ============================================================
INSERT INTO category (id, name, parent_id, sort_order) VALUES
(1, '招牌推荐', 0, 1),
(2, '湘味经典', 0, 2),
(3, '精选凉菜', 0, 3),
(4, '慢炖汤类', 0, 4),
(5, '特色主食', 0, 5),
(6, '时令甜品', 0, 6),
(7, '饮料酒水', 0, 7);

-- ============================================================
-- 2. 插入菜品数据（显式指定 ID，category_id 对应上方分类）
-- spicy_level: 0不辣 1微辣 2中辣 3重辣
-- is_signature: 0否 1是
-- status: 1上架
-- ============================================================

-- 招牌推荐（category_id = 1）
INSERT INTO dish (id, name, category_id, price, cost_price, spicy_level, is_signature, image_url, description, cultural_story, status) VALUES
(1,  '火宫殿臭豆腐', 1, 48.00,  0.00, 2, 1, 'https://modao.cc/agent-py/media/generated_images/2026-02-17/5524f6cb19284f87bc510f884f6541c1.jpg', '百年秘制卤水，外焦内嫩，闻着臭吃着香。', '经典长沙小吃，毛主席赞不绝口。', 1),
(2,  '姊妹团子',     1, 39.00,  0.00, 0, 0, 'https://img.phb123.com/uploads/allimg/220923/812-2209231141560-L.jpg', '外观白净、口感细腻油润、甜香、咸香。', '姊妹团子采用纯手工制作，鲜香爽口，深受食客喜爱。', 1),
(3,  '龙脂猪血',     1, 49.00,  0.00, 2, 1, 'http://img1.bala.cc/allimg/2308/1939413H8-3.jpg', '成品色泽红润，口感嫩滑如脂，兼具香脆辛辣与鲜爽风味。', '其名源于文化人以"龙肝凤脂"形容其嫩滑口感，需用肉骨鲜汤烹制。', 1),
(4,  '三角豆腐',     1, 66.00,  0.00, 0, 0, 'https://pic.huitu.com/pic/20210225/2473929_20210225172242397070_0.jpg', '炸制后香嫩鲜美。', '三角豆腐是以豆腐为主料制作的一道菜肴，其名称源于将方形豆腐对角切成三角形的形态。', 1),
(5,  '煮馓子',       1, 45.00,  0.00, 0, 0, 'https://ts4.tc.mm.bing.net/th/id/OIP-C.w91a7Ls4oL6Da3O7eIS41AHaFj?rs=1&pid=ImgDetMain&o=7&rm=3', '以肉汤冲沸，配牛肉码，落口消融。', '煮馓子是以面粉为主料的传统油炸面食，因形如折扇或花瓣、口感酥脆得名。', 1),
(6,  '红烧猪脚',     1, 126.00, 0.00, 2, 0, 'https://materials.cdn.bcebos.com/images/1755646/fa66057e78d2567833ce3747b773f2c9.jpeg', '慢煨至红亮肥糯，味浓鲜香。', '该小吃起源于清乾隆年间，由火宫殿刘氏用蚕豆磨粉制成白色粉坨，切片后加入汤料烹煮而成。', 1),
(7,  '荷兰粉',       1, 60.00,  0.00, 0, 0, 'https://pic.rmb.bdstatic.com/e5ed4944c3a90daafd9721f4e8bb18d3.bmp@h_1280', '用蚕豆粉制成玉色粉坨，汤鲜滑嫩。', '荷兰粉起源于火宫殿，以蚕豆粉为原料，汤底鲜美，深受食客喜爱。', 1),
(8,  '八宝果饭',     1, 88.00,  0.00, 0, 0, 'https://pic.nximg.cn/file/20150613/6133182_081113891396_2.jpg', '集糯米、湘莲、红枣等蒸制，软糯香甜。', '主料为糯米，配以红枣、湘莲、桂圆、青豆、桔饼、红瓜、冬瓜糖、葡萄干等八种食材制成。', 1),
(9,  '发丝牛百叶',   1, 188.00, 0.00, 0, 0, 'https://pics1.baidu.com/feed/34fae6cd7b899e514b362043236dc823c9950d02.jpeg@f_auto?token=b19471a5cd12b0fd564d3ba181d102e4', '以牛百叶切丝，配以冬笋丝、鲜红椒丝、蒜茸等辅料，急火爆炒而成。', '形如发丝，质地脆嫩，集咸、鲜、辣、酸于一体。此菜尤其讲究刀工，刀刀均匀，丝丝如发。', 1),
(10, '花菇无黄蛋',   1, 79.00,  0.00, 0, 0, 'https://ts4.tc.mm.bing.net/th/id/OIP-C.5gkiMoR30Le84FV8SJwSFAHaGK?rs=1&pid=ImgDetMain&o=7&rm=3', '成菜蛋面光滑不破，质地异常鲜嫩。', '由肖荣华创制，蛋面色白光滑，清雅诱人食欲。', 1);

-- 湘味经典（category_id = 2）
INSERT INTO dish (id, name, category_id, price, cost_price, spicy_level, is_signature, image_url, description, cultural_story, status) VALUES
(11, '腊肉炒萝卜干', 2, 58.00, 0.00, 2, 0, 'https://modao.cc/agent-py/media/generated_images/2026-02-17/914c3826d03549eeb4ad1d22c9519c15.jpg', '正宗农家烟熏腊肉，爽脆爽口，下饭神器。', '腊肉烟熏香浓，萝卜干脆甜，完美搭配。', 1),
(12, '招牌剁椒鱼头', 2, 22.00, 0.00, 0, 1, 'https://modao.cc/agent-py/media/generated_images/2026-02-17/0dcd79938a3c42ed857653023fde6284.jpg', '以鳙鱼鱼头、剁椒为主料，配豉油、姜、葱、蒜等辅料蒸制而成，色泽红亮，肉质细嫩软糯，鲜辣适口。', '剁椒鱼头是湖南最具代表性的传统名菜之一，以其鲜辣浓香闻名全国。', 1);

-- 精选凉菜（category_id = 3）
INSERT INTO dish (id, name, category_id, price, cost_price, spicy_level, is_signature, image_url, description, cultural_story, status) VALUES
(13, '老坛剁椒皮蛋', 3, 26.00, 0.00, 1, 0, 'https://so1.360tres.com/t0186ee2f3392dea91f.jpg', '松花皮蛋配陈醋剁椒，开胃凉菜。', '清爽开胃，下酒佳品。', 1);

-- 慢炖汤类（category_id = 4）
INSERT INTO dish (id, name, category_id, price, cost_price, spicy_level, is_signature, image_url, description, cultural_story, status) VALUES
(14, '湖藕炖筒子骨', 4, 78.00, 0.00, 0, 0, 'https://modao.cc/agent-py/media/generated_images/2026-02-17/e0456b60c7114336b59c6689e4086b20.jpg', '粉糯湖藕，筒子骨熬制，汤鲜味美。', '火候足，汤色浓，秋冬暖身。', 1);

-- 特色主食（category_id = 5）
INSERT INTO dish (id, name, category_id, price, cost_price, spicy_level, is_signature, image_url, description, cultural_story, status) VALUES
(15, '酱油蛋炒饭', 5, 22.00, 0.00, 0, 0, 'https://materials.cdn.bcebos.com/images/42380157/b4b4b7518e61c665a6c3dccb53dc50f8.jpeg', '粒粒分明，酱香浓郁。', '简单但考验火候，家常味道。', 1);

-- 时令甜品（category_id = 6）
INSERT INTO dish (id, name, category_id, price, cost_price, spicy_level, is_signature, image_url, description, cultural_story, status) VALUES
(16, '红糖粑粑', 6, 18.00, 0.00, 0, 0, 'https://img.alicdn.com/i3/2208059514480/O1CN01hRcAFa1ixszBWH6uF_!!2208059514480.jpg', '手工糍粑，红糖姜汁。', '暖宫暖胃，甜而不腻。', 1);
