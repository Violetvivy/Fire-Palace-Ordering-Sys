import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import { StatusBar as ExpoStatusBar } from 'expo-status-bar';
import { useState } from 'react';
import {
  Image,
  ImageBackground,
  Modal,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from 'react-native';

// 定义常用颜色常量
const COLORS = {
  red: '#B22222',
  gold: '#D4AF37',
  darkRed: '#801212',
};

export default function BindScreen() {
  const router = useRouter();
  const [showQuickSetup, setShowQuickSetup] = useState(false);
  const [roomCode, setRoomCode] = useState('');
  const [selectedPeople, setSelectedPeople] = useState('2');
  const [selectedScene, setSelectedScene] = useState('商务宴请');
  const [selectedAllergies, setSelectedAllergies] = useState<string[]>([]);
  const [selectedBudget, setSelectedBudget] = useState('不限');

  // 切换忌口标签
  const toggleAllergy = (item: string) => {
    setSelectedAllergies(prev =>
      prev.includes(item) ? prev.filter(i => i !== item) : [...prev, item]
    );
  };

  // 开始点餐，模拟加载后跳转
  const handleStartOrder = () => {
    // 这里可以添加实际逻辑，例如保存用户偏好
    router.replace('/(tabs)');
  };

  return (
    <ImageBackground
      source={{ uri: 'https://img.shetu66.com/2023/12/16/1702700953848478.jpg' }}
      className="flex-1"
      resizeMode="cover"
    >
      {/* 深色遮罩 */}
      <View className="absolute inset-0 bg-black/30" />
      {/* 传统纹样（用视图模拟） */}
      <View className="absolute inset-0 opacity-20 bg-pattern" />

      <View className="flex-1" >
        <ExpoStatusBar style="light" />

        {/* 顶部导航 */}
        <View className="h-16 bg-[#801212]/90 backdrop-blur-md flex-row items-center justify-between px-4 border-b border-[#D4AF37]/30">
          <View className="flex-row items-center gap-2">
            {/* <MaterialCommunityIcons name="lantern" size={28} color="white" /> */}
            <View className="flex-row items-center">
              <Image
                source={require('../assets/images/logo.png')}
                className="h-8 w-24"
                resizeMode="contain"
              />
              <Text className="text-white/80 text-xs ml-2">| 包厢贵宾点餐系统</Text>
            </View>
          </View>
          <View className="flex-row items-center gap-4">
            <View className="items-end">
              <Text className="text-white/70 text-xs">当前包厢：</Text>
              <Text className="text-white font-bold">岳阳楼 (VIP-08)</Text>
            </View>
            <View className="h-6 w-px bg-white/30" />
            <View className="flex-row items-center gap-1">
              <Ionicons name="people" size={18} color="white" />
              <Text className="text-white font-bold">8人用餐</Text>
            </View>
            <View className="h-6 w-px bg-white/30" />
            <TouchableOpacity className="flex-row items-center gap-1">
              <Ionicons name="language" size={18} color="white" />
              <Text className="text-white font-bold text-sm">中 / EN</Text>
            </TouchableOpacity>
            <View className="h-6 w-px bg-white/30" />
            <TouchableOpacity className="flex-row items-center gap-1">
              <Ionicons name="document-text" size={18} color="white" />
              <Text className="text-white font-bold text-sm">会员码</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* 主内容 */}
        <View className="flex-1 items-center justify-center">
          <View className="items-center mb-12">
            <Text className="text-white text-4xl font-serif mb-2">欢迎莅临火宫殿</Text>
            <View className="h-1 w-24 bg-[#B22222] rounded-full mb-4" />
            <Text className="text-amber-200 text-base text-center">
              请通过以下方式绑定您的贵宾包厢，开启 AI 智能点餐体验
            </Text>
          </View>

          <View className="w-[60%] flex-row gap-10">
            {/* 扫码绑定 */}
            <TouchableOpacity
              className="flex-1 bg-[#EDE2E1]/90 backdrop-blur-md rounded-3xl p-6 items-center border border-[#D4AF37]/25"
              style={{ shadowColor: '#000', shadowOpacity: 0.3, shadowRadius: 20, shadowOffset: { width: 0, height: 10 } }}
              onPress={() => setShowQuickSetup(true)}
            >
              <View className="absolute top-3 right-3 bg-green-500/30 px-2 py-1 rounded-full border border-green-500/40">
                <Text className="text-white text-xs">✨ 推荐方式</Text>
              </View>
              <View className="w-20 h-20 bg-white rounded-2xl items-center justify-center mb-4">
                <Ionicons name="qr-code" size={48} color={COLORS.red} />
              </View>
              <Text className="text-gray-800 text-xl font-bold mb-1">扫码绑定包厢</Text>
              <Text className="text-gray-600 text-center text-sm">使用微信或店刊扫描桌位二维码</Text>
            </TouchableOpacity>

            {/* 手动输入 */}
            <View className="flex-1 bg-[#EDE2E1]/90 backdrop-blur-md rounded-3xl p-6 border border-[#D4AF37]/30">
              <Text className="text-gray-600 text-xs mb-2 uppercase">Room Identification Code</Text>
              <TextInput
                className="w-full bg-white/60 border border-amber-200/60 rounded-2xl py-4 px-4 text-gray-800 text-2xl text-center"
                placeholder="输入4位包厢码 (如: 8888)"
                placeholderTextColor="#9CA3AF"
                value={roomCode}
                onChangeText={setRoomCode}
                maxLength={4}
                keyboardType="number-pad"
              />
              <TouchableOpacity
                className="w-full mt-4 py-4 rounded-2xl bg-[#B22222] items-center"
                onPress={() => setShowQuickSetup(true)}
              >
                <Text className="text-white text-lg font-bold">确认绑定</Text>
              </TouchableOpacity>
              <Text className="text-gray-500 text-xs text-center mt-3">手动输入适用于扫码失败等特殊场景</Text>
            </View>
          </View>

          <View className="flex-row mt-8 gap-6">
            <View className="flex-row items-center gap-2">
              <Ionicons name="shield-checkmark" size={18} color="#fbbf24" />
              <Text className="text-amber-100/80 text-xs">端到端加密传输，保护商务隐私</Text>
            </View>
            <View className="flex-row items-center gap-2">
              <Ionicons name="happy" size={18} color="#fbbf24" />
              <Text className="text-amber-100/80 text-xs">AI 实时营养分析与口味自适应</Text>
            </View>
          </View>
        </View>

        {/* 快速设置浮层 */}
        <Modal
          visible={showQuickSetup}
          transparent
          animationType="slide"
          onRequestClose={() => setShowQuickSetup(false)}
        >
          <View className="flex-1 bg-black/50 backdrop-blur-sm justify-end">
            <View className="bg-white/95 rounded-t-[40px] p-6 border-t border-amber-200/30">
              <View className="flex-row justify-between items-center mb-6">
                <View>
                  <Text className="text-gray-800 text-2xl font-serif flex-row items-center">
                    <Ionicons name="flash" size={24} color={COLORS.red} /> 🎯 快速配置本桌偏好
                  </Text>
                  <Text className="text-gray-500 text-sm">AI 将根据此设置动态调整菜品权重与套餐建议</Text>
                </View>
                <TouchableOpacity
                  className="w-10 h-10 bg-white/80 rounded-full items-center justify-center"
                  onPress={() => setShowQuickSetup(false)}
                >
                  <Ionicons name="close" size={24} color="#6B7280" />
                </TouchableOpacity>
              </View>

              {/* 两列布局 */}
              <View className="flex-row gap-4">
                {/* 左列 */}
                <View className="flex-1 space-y-6">
                  {/* 用餐人数 */}
                  <View>
                    <Text className="text-gray-600 text-sm mb-2">用餐人数 (人)</Text>
                    <View className="flex-row gap-2">
                      {['2', '4', '6', '8', '10+'].map(num => (
                        <TouchableOpacity
                          key={num}
                          className={`flex-1 h-12 rounded-xl border items-center justify-center ${selectedPeople === num ? 'bg-[#B22222] border-transparent' : 'bg-white/70 border-gray-200'}`}
                          onPress={() => setSelectedPeople(num)}
                        >
                          <Text className={selectedPeople === num ? 'text-white font-bold' : 'text-gray-800'}>{num}</Text>
                        </TouchableOpacity>
                      ))}
                    </View>
                  </View>

                  {/* 用餐场景 */}
                  <View>
                    <Text className="text-gray-600 text-sm mb-2">用餐场景</Text>
                    <View className="flex-row gap-2">
                      {[
                        { label: '商务宴请', icon: 'briefcase' },
                        { label: '家庭聚会', icon: 'people' },
                        { label: '挚友小酌', icon: 'wine' },
                      ].map(scene => (
                        <TouchableOpacity
                          key={scene.label}
                          className={`flex-1 h-12 rounded-xl border items-center justify-center flex-row gap-1 ${selectedScene === scene.label ? 'bg-[#B22222] border-transparent' : 'bg-white/70 border-gray-200'}`}
                          onPress={() => setSelectedScene(scene.label)}
                        >
                          <Ionicons name={scene.icon as any} size={18} color={selectedScene === scene.label ? 'white' : '#4B5563'} />
                          <Text className={selectedScene === scene.label ? 'text-white font-bold' : 'text-gray-800'}>{scene.label}</Text>
                        </TouchableOpacity>
                      ))}
                    </View>
                  </View>
                </View>

                {/* 右列 */}
                <View className="flex-1 space-y-6">
                  {/* 忌口标签 */}
                  <View>
                    <Text className="text-gray-600 text-sm mb-2">忌口偏好 (可多选)</Text>
                    <View className="flex-row flex-wrap gap-2">
                      {['不吃辣', '海鲜过敏', '清淡素食', '不要香菜', '低糖低脂', '+自定义'].map(item => (
                        <TouchableOpacity
                          key={item}
                          className={`px-4 h-9 rounded-full border items-center justify-center ${selectedAllergies.includes(item) ? 'bg-[#B22222] border-transparent' : 'bg-white/70 border-gray-200'}`}
                          onPress={() => toggleAllergy(item)}
                        >
                          <Text className={selectedAllergies.includes(item) ? 'text-white text-sm' : 'text-gray-700 text-sm'}>{item}</Text>
                        </TouchableOpacity>
                      ))}
                    </View>
                  </View>

                  {/* 预算 */}
                  <View>
                    <Text className="text-gray-600 text-sm mb-2">预算参考 (￥/桌)</Text>
                    <View className="flex-row gap-2">
                      {['不限', '800', '1500', '3000+', '+自定义'].map(budget => (
                        <TouchableOpacity
                          key={budget}
                          className={`flex-1 h-12 rounded-xl border items-center justify-center ${selectedBudget === budget ? 'bg-[#B22222] border-transparent' : 'bg-white/70 border-gray-200'}`}
                          onPress={() => setSelectedBudget(budget)}
                        >
                          <Text className={selectedBudget === budget ? 'text-white font-bold' : 'text-gray-800'}>{budget}</Text>
                        </TouchableOpacity>
                      ))}
                    </View>
                  </View>
                </View>
              </View>

              {/* 底部操作 */}
              <View className="mt-8 pt-6 border-t border-gray-200 flex-row justify-between items-center">
                <View className="flex-row items-center gap-2">
                  <Ionicons name="information-circle" size={20} color={COLORS.red} />
                  <Text className="text-gray-600 text-xs italic">
                    AI 提示：此配置仅限本桌，将作为智能推荐算法的核心依据
                  </Text>
                </View>
                <TouchableOpacity
                  className="bg-[#B22222] px-8 py-4 rounded-2xl flex-row items-center gap-2"
                  onPress={handleStartOrder}
                >
                  <Text className="text-white text-lg font-bold">开始点餐</Text>
                  <Ionicons name="arrow-forward" size={20} color="white" />
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </Modal>

        {/* 页脚 */}
        <Text className="text-center text-amber-100/60 text-[10px] tracking-widest pb-2">
          © 2026 火宫殿 | 数字化正餐点餐标准 4.0 | 隐私保护与安全加密
        </Text>
      </View>
    </ImageBackground>
  );
}