import { MaterialIcons } from '@expo/vector-icons'; // 用于图标，稳定可靠
import React, { useEffect, useRef, useState } from 'react';
import {
  Animated,
  Dimensions,
  Image,
  ScrollView,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';

// 分类数据
const categories = [
  { id: 'special', label: '招牌推荐', icon: 'local-dining', count: '3 道点过' },
  { id: 'classic', label: '湘味经典', icon: 'restaurant', count: '2 道点过' },
  { id: 'cold', label: '精选凉菜', icon: 'ac-unit', count: '未选' },
  { id: 'soup', label: '慢炖汤类', icon: 'soup-kitchen', count: '未选' },
  { id: 'stable', label: '特色主食', icon: 'star', count: '未选' },
  { id: 'dessert', label: '时令甜品', icon: 'cake', count: '' },
  { id: 'drink', label: '饮料酒水', icon: 'local-drink', count: '' },
];

// 菜品数据（从 HTML 复制）
const dishData = {
  special: [
    {
      id: 1,
      name: '火宫殿臭豆腐',
      desc: '百年秘制卤水，外焦内嫩，闻着臭吃着香。',
      price: 48,
      img: 'https://modao.cc/agent-py/media/generated_images/2026-02-17/5524f6cb19284f87bc510f884f6541c1.jpg',
      tags: ['招牌', '本桌最爱'],
      spicy: '中辣',
      portion: '3-4人',
      ai: '经典长沙小吃，毛主席赞不绝口。',
    },
    {
      id: 2,
      name: '姊妹团子',
      desc: '外观白净、口感细腻油润、甜香、咸香。',
      price: 39,
      img: 'https://img.phb123.com/uploads/allimg/220923/812-2209231141560-L.jpg',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '姊妹团子采用纯手工制作，鲜香爽口，深受食客喜爱。',
    },
    {
      id: 3,
      name: '龙脂猪血',
      desc: '成品色泽红润，口感嫩滑如脂，兼具香脆辛辣与鲜爽风味。',
      price: 49,
      img: 'http://img1.bala.cc/allimg/2308/1939413H8-3.jpg',
      tags: ['招牌'],
      spicy: '中辣',
      portion: '3-4人',
      ai: '其名源于文化人以“龙肝凤脂”形容其嫩滑口感，需用肉骨鲜汤烹制',
    },
    {
      id: 4,
      name: '三角豆腐',
      desc: '炸制后香嫩鲜美。',
      price: 66,
      img: 'https://pic.huitu.com/pic/20210225/2473929_20210225172242397070_0.jpg',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '三角豆腐是以豆腐为主料制作的一道菜肴，其名称源于将方形豆腐对角切成三角形的形态。',
    },
    {
      id: 5,
      name: '煮馓子',
      desc: '以肉汤冲沸，配牛肉码，落口消融。',
      price: 45,
      img: 'https://ts4.tc.mm.bing.net/th/id/OIP-C.w91a7Ls4oL6Da3O7eIS41AHaFj?rs=1&pid=ImgDetMain&o=7&rm=3',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '煮馓子是以面粉为主料的传统油炸面食，因形如折扇或花瓣、口感酥脆得名。',
    },
    {
      id: 6,
      name: '红烧猪脚',
      desc: '慢煨至红亮肥糯，味浓鲜香。',
      price: 126,
      img: 'https://materials.cdn.bcebos.com/images/1755646/fa66057e78d2567833ce3747b773f2c9.jpeg',
      tags: ['点击量TOP'],
      spicy: '中辣',
      portion: '1-3人',
      ai: '该小吃起源于清乾隆年间，由火宫殿刘氏用蚕豆磨粉制成白色粉坨，切片后加入汤料烹煮而成。',
    },
    {
      id: 7,
      name: '荷兰粉',
      desc: '用蚕豆粉制成玉色粉坨，汤鲜滑嫩。',
      price: 60,
      img: 'https://pic.rmb.bdstatic.com/e5ed4944c3a90daafd9721f4e8bb18d3.bmp@h_1280',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '姊妹团子采用纯手工制作，鲜香爽口,深受食客喜爱。',
    },
    {
      id: 8,
      name: '八宝果饭',
      desc: '集糯米、湘莲、红枣等蒸制，软糯香甜。',
      price: 88,
      img: 'https://pic.nximg.cn/file/20150613/6133182_081113891396_2.jpg',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '主料为糯米，配以红枣、湘莲、桂圆、青豆、桔饼、红瓜、冬瓜糖、葡萄干等八种食材制成。',
    },
    {
      id: 9,
      name: '发丝牛百叶',
      desc: '以牛百叶切丝，配以冬笋丝、鲜红椒丝、蒜茸等辅料，加入盐、味精、香油等调料急火爆炒而成。',
      price: 188,
      img: 'https://pics1.baidu.com/feed/34fae6cd7b899e514b362043236dc823c9950d02.jpeg@f_auto?token=b19471a5cd12b0fd564d3ba181d102e4',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '形如发丝，质地脆嫩，集咸、鲜、辣、酸于一体。此菜尤其讲究刀工，刀刀均匀，丝丝如发。',
    },
    {
      id: 10,
      name: '花菇无黄蛋',
      desc: '成菜蛋面光滑不破，质地异常鲜嫩。',
      price: 79,
      img: 'https://ts4.tc.mm.bing.net/th/id/OIP-C.5gkiMoR30Le84FV8SJwSFAHaGK?rs=1&pid=ImgDetMain&o=7&rm=3',
      tags: ['点击量TOP'],
      spicy: '不辣',
      portion: '1-3人',
      ai: '由肖荣华创制，蛋面色白光滑，清雅诱人食欲。',
    },
  ],
  classic: [
    {
      id: 11,
      name: '腊肉炒萝卜干',
      desc: '正宗农家烟熏腊肉，爽脆爽口，下饭神器。',
      price: 58,
      img: 'https://modao.cc/agent-py/media/generated_images/2026-02-17/914c3826d03549eeb4ad1d22c9519c15.jpg',
      tags: ['下饭'],
      spicy: '中辣',
      portion: '2-3人',
      ai: '腊肉烟熏香浓，萝卜干脆甜，完美搭配。',
    },
    {
      id: 12,
      name: '招牌剁椒鱼头',
      desc: '以鳙鱼鱼头、剁椒为主料，配豉油、姜、葱、蒜等辅料蒸制而成，成品色泽红亮，肉质细嫩软糯，鲜辣适口。',
      price: 22,
      img: 'https://modao.cc/agent-py/media/generated_images/2026-02-17/0dcd79938a3c42ed857653023fde6284.jpg',
      tags: ['特色'],
      spicy: '不辣',
      portion: '2-3人',
      ai: '糯米粉团油炸挂糖，外酥里糯。',
    },
  ],
  cold: [
    {
      id: 13,
      name: '老坛剁椒皮蛋',
      desc: '松花皮蛋配陈醋剁椒，开胃凉菜。',
      price: 26,
      img: 'https://so1.360tres.com/t0186ee2f3392dea91f.jpg',
      tags: ['凉菜'],
      spicy: '微辣',
      portion: '2-3人',
      ai: '清爽开胃，下酒佳品。',
    },
  ],
  soup: [
    {
      id: 14,
      name: '湖藕炖筒子骨',
      desc: '粉糯湖藕，筒子骨熬制，汤鲜味美。',
      price: 78,
      img: 'https://modao.cc/agent-py/media/generated_images/2026-02-17/e0456b60c7114336b59c6689e4086b20.jpg',
      tags: ['慢炖'],
      spicy: '不辣',
      portion: '4-5人',
      ai: '火候足，汤色浓，秋冬暖身。',
    },
  ],
  stable: [
    {
      id: 15,
      name: '酱油蛋炒饭',
      desc: '粒粒分明，酱香浓郁。',
      price: 22,
      img: 'https://materials.cdn.bcebos.com/images/42380157/b4b4b7518e61c665a6c3dccb53dc50f8.jpeg',
      tags: ['主食'],
      spicy: '不辣',
      portion: '2人',
      ai: '简单但考验火候，家常味道。',
    },
  ],
  dessert: [
    {
      id: 16,
      name: '红糖粑粑',
      desc: '手工糍粑，红糖姜汁。',
      price: 18,
      img: 'https://img.alicdn.com/i3/2208059514480/O1CN01hRcAFa1ixszBWH6uF_!!2208059514480.jpg',
      tags: ['甜品'],
      spicy: '不辣',
      portion: '2人',
      ai: '暖宫暖胃，甜而不腻。',
    },
  ],
};

// 模拟购物车数据
const initialCart = [
  { id: 1, name: '火宫殿臭豆腐', price: 28, quantity: 1, img: dishData.special[0].img },
  { id: 2, name: '毛氏红烧肉', price: 128, quantity: 1, img: dishData.special[1].img },
  { id: 3, name: '剁椒鱼头', price: 188, quantity: 1, img: dishData.special[2].img },
];

// 屏幕宽度常量
const SCREEN_WIDTH = Dimensions.get('window').width;

export default function IndexScreen() {
  const [activeCategory, setActiveCategory] = useState('special');
  const [cart, setCart] = useState(initialCart);
  const [cartExpanded, setCartExpanded] = useState(true); // 默认展开

  // 动画值
  const cartWidthAnim = useRef(new Animated.Value(288)).current; // w-72 = 288px
  const cartExpandedWidth = 288; // 展开宽度
  const cartCollapsedWidth = 56; // 折叠宽度（仅图标）

  useEffect(() => {
    Animated.timing(cartWidthAnim, {
      toValue: cartExpanded ? cartExpandedWidth : cartCollapsedWidth,
      duration: 300,
      useNativeDriver: false, // 宽度动画不能使用 native driver
    }).start();
  }, [cartExpanded]);

  // 计算购物车总计
  const cartTotal = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const cartCount = cart.reduce((sum, item) => sum + item.quantity, 0);

  // 切换购物车展开/折叠
  const toggleCart = () => setCartExpanded(!cartExpanded);

  // 处理添加菜品到购物车
  const addToCart = (dish) => {
    setCart((prev) => {
      const existing = prev.find((item) => item.id === dish.id);
      if (existing) {
        return prev.map((item) =>
          item.id === dish.id ? { ...item, quantity: item.quantity + 1 } : item,
        );
      } else {
        return [...prev, { ...dish, quantity: 1 }];
      }
    });
  };

  // 更新数量
  const updateQuantity = (id: number, delta: number) => {
    setCart((prev) => {
      return prev
        .map((item) => {
          if (item.id === id) {
            const newQty = item.quantity + delta;
            return newQty > 0 ? { ...item, quantity: newQty } : null;
          }
          return item;
        })
        .filter(Boolean);
    });
  };

  const CategoryButton = ({ cat, isActive, onPress }) => (
    <TouchableOpacity
      className={`flex-col items-center py-6 border-b border-neutral-100 ${
        isActive ? 'bg-[#801212]' : ''
      }`}
      style={isActive && { borderRightWidth: 4, borderRightColor: '#FFD700' }}
      onPress={onPress}
    >
      <MaterialIcons
        name={cat.icon}
        size={24}
        color={isActive ? 'white' : '#4B5563'}
        style={{ marginBottom: 8 }}
      />
      <Text className={`text-sm font-bold ${isActive ? 'text-white' : 'text-neutral-600'}`}>
        {cat.label}
      </Text>
      {cat.count && (
        <Text className={`text-[10px] mt-1 ${isActive ? 'text-white/60' : 'text-neutral-400'}`}>
          {cat.count}
        </Text>
      )}
    </TouchableOpacity>
  );

  const DishCard = ({ dish }) => (
    <View className="bg-white rounded-xl overflow-hidden shadow-sm mb-4 mx-2 w-[48%]">
      <View className="relative h-64">
        <Image source={{ uri: dish.img }} className="w-full h-full" resizeMode="cover" />
        <View className="absolute top-2 left-2 flex-row flex-wrap gap-1">
          {dish.tags.map((tag: String, idx: number) => (
            <View key={idx} className="bg-[#B22222] px-2 py-0.5 rounded-full">
              <Text className="text-white text-[8px]">{tag}</Text>
            </View>
          ))}
        </View>
      </View>
      <View className="p-3">
        <Text className="font-serif font-bold text-base text-neutral-800">{dish.name}</Text>
        <Text className="text-xs text-neutral-500 mt-1" numberOfLines={1}>
          {dish.desc}
        </Text>
        <View className="mt-3 flex-row justify-between items-center">
          <Text className="text-[#B22222] font-bold text-lg">¥{dish.price}</Text>
          <TouchableOpacity
            className="bg-[#B22222] p-2 rounded-full"
            onPress={() => addToCart(dish)}
          >
            <MaterialIcons name="add" size={16} color="white" />
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );

  const renderDishGrid = () => {
    const dishes = dishData[activeCategory] || [];
    return (
      <View className="flex-row flex-wrap justify-between px-2">
        {dishes.map((dish) => (
          <DishCard key={dish.id} dish={dish} />
        ))}
      </View>
    );
  };

  // 折叠状态下的右侧内容（仅图标）
  const CollapsedCart = () => (
    <View className="flex-1 items-center justify-start pt-8">
      <TouchableOpacity onPress={toggleCart} className="items-center">
        <View className="bg-[#B22222] p-3 rounded-full mb-2">
          <MaterialIcons name="shopping-cart" size={24} color="white" />
        </View>
        {cartCount > 0 && (
          <View className="absolute -top-1 -right-1 bg-[#B22222] rounded-full w-5 h-5 items-center justify-center border border-white">
            <Text className="text-white text-xs font-bold">{cartCount}</Text>
          </View>
        )}
        <Text className="text-xs text-neutral-600 mt-2">购物车</Text>
      </TouchableOpacity>
    </View>
  );

  // 展开状态下的购物车内容
  const ExpandedCart = () => (
    <>
      <View className="p-4 bg-neutral-50 border-b">
        <View className="flex-row items-center justify-between mb-2">
          <View className="flex-row items-center gap-2">
            <TouchableOpacity onPress={toggleCart}>
              <MaterialIcons name="chevron-right" size={20} color="#B22222" />
            </TouchableOpacity>
            <Text className="font-bold text-neutral-800">已选菜品</Text>
          </View>
          <View className="bg-[#B22222] px-2 py-0.5 rounded-full">
            <Text className="text-white text-xs font-bold">{cartCount}</Text>
          </View>
        </View>
        <Text className="text-2xl font-bold text-[#B22222]">¥{cartTotal}</Text>
      </View>

      {/* AI 提示区 */}
      <View className="px-4 py-3 space-y-2">
        <View className="bg-amber-50 rounded-lg p-3 border border-amber-100">
          <View className="flex-row items-start gap-2">
            <MaterialIcons name="error" size={18} color="#F59E0B" style={{ marginTop: 2 }} />
            <View className="flex-1">
              <Text className="text-xs text-amber-900 font-bold">营养结构不完整</Text>
              <Text className="text-[11px] text-amber-700 mt-1">
                建议增加汤品或主食，以平衡口味。
              </Text>
              <View className="flex-row gap-2 mt-2">
                <TouchableOpacity
                  className="bg-amber-500 px-2 py-1 rounded"
                  onPress={() => setActiveCategory('soup')}
                >
                  <Text className="text-white text-[10px]">一键补汤</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  className="border border-amber-500 px-2 py-1 rounded"
                  onPress={() => setActiveCategory('stable')}
                >
                  <Text className="text-amber-500 text-[10px]">补主食</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </View>
        <View className="bg-green-50 rounded-lg p-3 border border-green-100">
          <View className="flex-row items-start gap-2">
            <MaterialIcons name="check-circle" size={18} color="#10B981" style={{ marginTop: 2 }} />
            <View className="flex-1">
              <Text className="text-xs text-green-900 font-bold">辣度动态调节</Text>
              <Text className="text-[11px] text-green-700 mt-1">
                已同步本桌“不吃辣”标签，已为您减少3道菜的辣度。
              </Text>
            </View>
          </View>
        </View>
      </View>

      {/* 购物车列表 */}
      <ScrollView className="flex-1 px-4 py-2">
        {cart.map((item) => (
          <View key={item.id} className="flex-row justify-between items-center mb-3">
            <View className="flex-1">
              <Text className="text-neutral-800 text-sm" numberOfLines={1}>
                {item.name}
              </Text>
              <Text className="text-[10px] text-neutral-400">
                ¥{item.price} x {item.quantity}
              </Text>
            </View>
            <View className="flex-row items-center gap-3">
              <TouchableOpacity onPress={() => updateQuantity(item.id, -1)}>
                <MaterialIcons name="remove-circle-outline" size={20} color="#9CA3AF" />
              </TouchableOpacity>
              <Text className="text-xs w-5 text-center">{item.quantity}</Text>
              <TouchableOpacity onPress={() => updateQuantity(item.id, 1)}>
                <MaterialIcons name="add-circle" size={20} color="#B22222" />
              </TouchableOpacity>
            </View>
          </View>
        ))}
      </ScrollView>

      {/* 底部按钮 */}
      <View className="p-4 border-t space-y-3">
        <TouchableOpacity className="w-full py-4 rounded-xl flex-row items-center justify-center gap-2 border-2 border-[#B22222]">
          <MaterialIcons name="smart-toy" size={20} color="#B22222" />
          <Text className="text-[#B22222] font-bold">AI 套餐生成器</Text>
        </TouchableOpacity>
        <TouchableOpacity
          className="w-full py-4 rounded-xl flex-row items-center justify-center gap-2 bg-gradient-to-r from-yellow-500 to-yellow-600"
          style={{ backgroundColor: '#D4AF37' }}
        >
          <Text className="text-white font-bold">立即下单确认</Text>
          <MaterialIcons name="chevron-right" size={20} color="white" />
        </TouchableOpacity>
      </View>
    </>
  );

  return (
    <View className="flex-1 flex-row bg-[#F8F5F2]">
      {/* 左侧分类导航 */}
      <View className="w-32 bg-white border-r border-neutral-200">
        <ScrollView showsVerticalScrollIndicator={false}>
          {categories.map((cat) => (
            <CategoryButton
              key={cat.id}
              cat={cat}
              isActive={activeCategory === cat.id}
              onPress={() => setActiveCategory(cat.id)}
            />
          ))}
        </ScrollView>
      </View>

      {/* 中间菜品区域 */}
      <View className="flex-1">
        {/* 标题和搜索 */}
        <View className="flex-row justify-between items-center px-6 py-4">
          <Text className="text-2xl font-serif font-bold text-[#B22222] border-l-4 border-[#B22222] pl-3">
            {categories.find((c) => c.id === activeCategory)?.label || '招牌必点'}
          </Text>
          <View className="relative">
            <TextInput
              className="bg-white border text-sm rounded-full py-2 pl-10 pr-4 w-64"
              placeholder="搜索菜名/原料..."
              placeholderTextColor="#9CA3AF"
            />
            <MaterialIcons
              name="search"
              size={20}
              color="#9CA3AF"
              style={{ position: 'absolute', left: 12, top: 10 }}
            />
          </View>
        </View>

        {/* 菜品网格 */}
        <ScrollView className="flex-1 px-2" showsVerticalScrollIndicator={false}>
          {renderDishGrid()}
        </ScrollView>
      </View>

      {/* 右侧购物车 - 动画宽度 */}
      <Animated.View
        style={{ width: cartWidthAnim, backgroundColor: 'white' }}
        className="shadow-[-4px_0_10px_rgba(0,0,0,0.02)]"
      >
        {cartExpanded ? <ExpandedCart /> : <CollapsedCart />}
      </Animated.View>
    </View>
  );
}
