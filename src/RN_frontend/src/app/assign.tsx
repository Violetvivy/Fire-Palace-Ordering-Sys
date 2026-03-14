import useAuthStore from '@/stores/useAuthStore';
import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import { StatusBar as ExpoStatusBar } from 'expo-status-bar';
import {
  Image,
  ImageBackground,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

const COLORS = {
  red: '#B22222',
  gold: '#D4AF37',
  darkRed: '#801212',
};

//包厢分配
export default function Assign() {
  const router = useRouter();
  const Logout = useAuthStore((state) => state.logout);
  const handleLogout = async () => {
    Logout();
    router.replace('/');
  }
  return (
    <ImageBackground
      source={{ uri: 'https://img.shetu66.com/2023/12/16/1702700953848478.jpg' }}
      className="flex-1"
      resizeMode="cover"
    >
      <View className="absolute inset-0 bg-black/30" />
      <View className="absolute inset-0 opacity-20 bg-pattern" />

      <View className="flex-1">
        <ExpoStatusBar style="light" />

        {/* 顶部导航保持不变 */}
        <View className="h-16 bg-[#801212]/90 backdrop-blur-md flex-row items-center justify-between px-4 border-b border-[#D4AF37]/30">
          <View className="flex-row items-center gap-2">
            <View className="flex-row items-center">
              <Image
                source={require('../assets/images/logo.png')}
                className="h-8 w-24"
                resizeMode="contain"
              />
              <Text className="text-white/80 text-xs ml-2">| 包厢分配</Text>
            </View>
          </View>

          <View className="flex-row items-center gap-6">
          <View className="flex-row items-center gap-2">
            <Ionicons name="person-circle-outline" size={24} color="white" />
            <Text className="text-white font-bold text-sm">管理员</Text>
          </View>
          {/* 退出登录 */}
          <TouchableOpacity className="flex-row items-center gap-2"
            onPress={handleLogout}
          >
            <Ionicons name="exit-outline" size={24} color="white" />
            <Text className="text-white font-bold text-sm">退出登录</Text>
          </TouchableOpacity>
          </View>
        </View>

      </View>
    </ImageBackground>
  );
}