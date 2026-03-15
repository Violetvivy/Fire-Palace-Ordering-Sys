import { adminLogin } from '@/services/admin';
import useAdminStore from '@/stores/useAdminStore';
import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import { StatusBar as ExpoStatusBar } from 'expo-status-bar';
import { useState } from 'react';
import {
  ActivityIndicator,
  Alert,
  Image,
  ImageBackground,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';

const COLORS = {
  red: '#B22222',
  gold: '#D4AF37',
  darkRed: '#801212',
};

export default function AdminLoginScreen() {
  const router = useRouter();
  const [adminname, setUsername] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const setAdminInfo = useAdminStore((state) => state.setAdminInfo);

  const handleLogin = async () => {
    if (!adminname.trim() || !password.trim() || !phone.trim()) {
      Alert.alert('提示', '请输入用户名、密码和手机号');
      return;
    }

    setIsLoading(true);
    try {
      const response = await adminLogin({ adminname, password, phone });
      if (response.code === 1) {
        // 登录成功，保存管理员信息
        const { token, userId, username } = response.data;
        setAdminInfo({ token, adminId: userId, adminname: username, phone });

        // 跳转包厢分配页面
        router.replace('/assign');
      } else {
        Alert.alert('登录失败', response.msg || '用户名或密码错误');
      }
    } catch (error: any) {
      Alert.alert('错误', error?.message || '网络请求失败，请检查网络');
    } finally {
      setIsLoading(false);
    }
  };

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
              <Text className="text-white/80 text-xs ml-2">| 系统登录</Text>
            </View>
          </View>
          <View className="flex-row items-center gap-2">
            <Ionicons name="person-circle-outline" size={24} color="white" />
            <Text className="text-white font-bold text-sm">管理员</Text>
          </View>
          
        </View>

        {/* 登录卡片 */}
        <View className="flex-1 items-center justify-center px-4">
          <View
            className="w-full max-w-md bg-[#EDE2E1]/90 backdrop-blur-md rounded-3xl p-8 border border-[#D4AF37]/30"
            style={{ shadowColor: '#000', shadowOpacity: 0.2, shadowRadius: 20, shadowOffset: { width: 0, height: 8 } }}
          >
            <View className="items-center mb-6">
              <View className="w-16 h-16 bg-white/20 rounded-2xl items-center justify-center mb-3 border border-[#D4AF37]/40">
                <Ionicons name="lock-closed" size={36} color={COLORS.red} />
              </View>
              <Text className="text-gray-800 text-3xl font-serif">管理员登录</Text>
              <View className="h-0.5 w-16 bg-[#B22222] rounded-full mt-2" />
            </View>

            {/* 用户名 */}
            <View className="mb-4">
              <Text className="text-gray-600 text-sm mb-1 ml-1">用户名</Text>
              <View className="flex-row items-center bg-white/80 rounded-xl border border-amber-200/60 px-4">
                <Ionicons name="person-outline" size={18} color="#9CA3AF" />
                <TextInput
                  className="flex-1 py-3 px-2 text-gray-800"
                  placeholder="请输入管理员账号"
                  placeholderTextColor="#9CA3AF"
                  value={adminname}
                  onChangeText={setUsername}
                  autoCapitalize="none"
                  editable={!isLoading}
                />
              </View>
            </View>

            <View className="mb-4">
              <Text className="text-gray-600 text-sm mb-1 ml-1">手机号</Text>
              <View className="flex-row items-center bg-white/80 rounded-xl border border-amber-200/60 px-4">
                <Ionicons name="phone-portrait-outline" size={18} color="#9CA3AF" />
                <TextInput
                  className="flex-1 py-3 px-2 text-gray-800"
                  placeholder="请输入管理员手机号"
                  placeholderTextColor="#9CA3AF"
                  value={phone}
                  onChangeText={setPhone}
                  autoCapitalize="none"
                  editable={!isLoading}
                />
              </View>
            </View>

            {/* 密码 */}
            <View className="mb-8">
              <Text className="text-gray-600 text-sm mb-1 ml-1">密码</Text>
              <View className="flex-row items-center bg-white/80 rounded-xl border border-amber-200/60 px-4">
                <Ionicons name="key-outline" size={18} color="#9CA3AF" />
                <TextInput
                  className="flex-1 py-3 px-2 text-gray-800"
                  placeholder="请输入密码"
                  placeholderTextColor="#9CA3AF"
                  secureTextEntry
                  value={password}
                  onChangeText={setPassword}
                  editable={!isLoading}
                />
              </View>
            </View>

            {/* 登录按钮 */}
            <TouchableOpacity
              className={`w-full py-4 rounded-xl bg-[#B22222] items-center flex-row justify-center gap-2 ${isLoading ? 'opacity-70' : ''}`}
              onPress={handleLogin}
              disabled={isLoading}
            >
              {isLoading ? (
                <ActivityIndicator size="small" color="white" />
              ) : (
                <>
                  <Ionicons name="log-in-outline" size={20} color="white" />
                  <Text className="text-white text-lg font-bold">登 录</Text>
                </>
              )}
            </TouchableOpacity>

            <Text className="text-gray-500 text-xs text-center mt-4">
              火宫殿 · 内部管理通道
            </Text>
          </View>
        </View>
      </View>
    </ImageBackground>
  );
}