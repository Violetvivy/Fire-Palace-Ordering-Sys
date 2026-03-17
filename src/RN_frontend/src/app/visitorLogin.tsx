import AsyncStorage from '@react-native-async-storage/async-storage';
import { useRouter } from 'expo-router';
import { StatusBar as ExpoStatusBar } from 'expo-status-bar';
import { SymbolView } from 'expo-symbols';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import {
  ActivityIndicator,
  Alert,
  FlatList,
  Image,
  ImageBackground,
  Modal,
  Pressable,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';

// 接口
import { guestLogin, userLogin, userRegister, UserRegisterParams } from '@/services/auth';
import { bindRoom, getAllRoom, roomInfo, unbindRoom } from '@/services/room';
import useAdminStore from '@/stores/useAdminStore';
import useUserStore from '@/stores/useUserStore';

const COLORS = {
  red: '#B22222',
  gold: '#D4AF37',
  darkRed: '#801212',
};

// 存储当前绑定包厢的 key
const BOUND_ROOM_KEY = 'bound_room';

export default function VisitorLoginScreen() {
  const router = useRouter();
  const { t, i18n } = useTranslation();
  const adminInfo = useAdminStore((state) => state.adminInfo);
  const { setUserInfo } = useUserStore();

  // 登录相关状态
  const [phone, setPhone] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [showRegister, setShowRegister] = useState(false);
  const [registerParams, setRegisterParams] = useState<UserRegisterParams>({
    phone: '',
    role: 1,
    username: '',
  });

  // 包厢绑定相关状态
  const [boundRoom, setBoundRoom] = useState<roomInfo | null>(null);
  const [availableRooms, setAvailableRooms] = useState<roomInfo[]>([]);
  const [showRoomPicker, setShowRoomPicker] = useState(false);
  const [showUnbindConfirm, setShowUnbindConfirm] = useState(false);

  // 加载已保存的绑定包厢
  useEffect(() => {
    const loadBoundRoom = async () => {
      const saved = await AsyncStorage.getItem(BOUND_ROOM_KEY);
      if (saved) {
        setBoundRoom(JSON.parse(saved));
      }
    };
    loadBoundRoom();
  }, []);

  // 语言切换
  const toggleLanguage = async () => {
    const newLang = i18n.language === 'en' ? 'zh' : 'en';
    await i18n.changeLanguage(newLang);
    await AsyncStorage.setItem('user-language', newLang);
  };

  // 打开包厢选择器
  const openRoomPicker = async () => {
    try {
      const res = await getAllRoom();
      if (res.code === 1) {
        // 过滤出未绑定的包厢（status === 0）
        const unbound = res.data.filter((r) => r.status === 0);
        setAvailableRooms(unbound);
        setShowRoomPicker(true);
      } else {
        Alert.alert(t('common.failed'), res.msg || '获取包厢列表失败');
      }
    } catch (error) {
      Alert.alert(t('common.error'), '网络请求失败');
    }
  };

  // 绑定包厢
  const handleBindRoom = async (room: roomInfo) => {
    try {
      const res = await bindRoom(room.roomName); // 假设 bindRoom 接受 roomName
      if (res.code === 1) {
        setBoundRoom(room);
        await AsyncStorage.setItem(BOUND_ROOM_KEY, JSON.stringify(room));
        setShowRoomPicker(false);
        Alert.alert(t('common.success'), t('alertMsg.bindSuccess')+`:${room.roomName}`);
      } else {
        Alert.alert(t('common.failed'), res.msg || '绑定失败');
      }
    } catch (error) {
      Alert.alert(t('common.error'), '网络请求失败');
    }
  };

  // 解绑包厢
  const handleUnbindRoom = async () => {
    if (!boundRoom) return;
    try {
      const res = await unbindRoom(boundRoom.roomName);
      if (res.code === 1) {
        setBoundRoom(null);
        await AsyncStorage.removeItem(BOUND_ROOM_KEY);
        setShowUnbindConfirm(false);
        Alert.alert(t('common.success'), t('alertMsg.unbindSuccess'));
      } else {
        Alert.alert(t('common.failed'), res.msg || '解绑失败');
      }
    } catch (error) {
      Alert.alert(t('common.error'), '网络请求失败');
    }
  };

  // 会员登录
  const handleMemberLogin = async () => {
    if (!phone.trim()) {
      Alert.alert(t('common.notice'), t('login.pleaseEnter')+t('login.phoneNumber'));
      return;
    }
    if(!boundRoom){
      Alert.alert(t('common.notice'), t('alertMsg.bindBoxFirst'));
      return;
    }
    setIsLoading(true);
    try {
      const res = await userLogin(phone);
      if (res.code === 1) {
        const { token, userId, phone: userPhone } = res.data;
        setUserInfo({
          userId,
          phone: userPhone,
          token,
          type: 'member',
        });
        Alert.alert(t('common.success'), t('login.loginSuccess'));
        router.replace('/(tabs)');
      } else {
        Alert.alert(t('common.failed'), res.msg);
      }
    } catch (error) {
      Alert.alert(t('common.error'), '网络请求失败');
    } finally {
      setIsLoading(false);
    }
  };

  // 游客登录
  const handleGuestLogin = async () => {
    if(!boundRoom){
      Alert.alert(t('common.notice'), t('alertMsg.bindBoxFirst'));
      return;
    }
    setIsLoading(true);
    try {
      const res = await guestLogin();
      if (res.code === 1) {
        const { token, userId, phone } = res.data;
        setUserInfo({
          userId,
          phone,
          token,
          type: 'guest',
        });
        Alert.alert(t('common.success'), t('login.loginSuccess'));
        router.replace('/(tabs)');
      } else {
        Alert.alert(t('common.failed'), res.msg);
      }
    } catch (error) {
      Alert.alert(t('common.error'), '网络请求失败');
    } finally {
      setIsLoading(false);
    }
  };

  // 注册会员
  const handleRegister = async () => {
    const { phone, username } = registerParams;
    if (!phone || !username) {
      Alert.alert(t('common.notice'), t('alertMsg.completeInfor'));
      return;
    }
    setIsLoading(true);
    try {
      const res = await userRegister(registerParams);
      if (res.code === 1) {
        Alert.alert(t('common.success'), '注册成功，请登录');
        setShowRegister(false);
        setRegisterParams({ phone: '', role: 1, username: '' });
      } else {
        Alert.alert(t('common.failed'), res.msg);
      }
    } catch (error) {
      Alert.alert(t('common.error'), '网络请求失败');
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

        {/* 顶部导航栏 */}
        <View className="h-16 bg-[#801212] flex-row items-center justify-between px-6">
          {/* 左侧 Logo */}
          <View className="flex-row items-center gap-3">
            <Image
              source={require('@/assets/images/logo.png')}
              className="h-8 w-24"
              resizeMode="contain"
            />
            <Text className="text-white text-sm ml-2 opacity-80">| {t('header.systemName')}</Text>
          </View>

          {/* 右侧功能按钮 */}
          <View className="flex-row items-center gap-4">
            {/* 包厢绑定/显示区域 */}
            {boundRoom ? (
              <Pressable
                className="flex-row items-center gap-2 bg-[#B22222]/80 px-3 py-1.5 rounded-full"
                onPress={() => setShowUnbindConfirm(true)}
              >
                <SymbolView name="door.left.hand.closed" size={16} tintColor="white" />
                <Text className="text-white font-bold text-sm">{boundRoom.roomName}</Text>
              </Pressable>
            ) : (
              <Pressable
                className="flex-row items-center gap-2 bg-[#D4AF37] px-3 py-1.5 rounded-full"
                onPress={openRoomPicker}
              >
                <SymbolView name="door.garage.open" size={16} tintColor={COLORS.darkRed} />
                <Text className="text-[#801212] font-bold text-sm">{t('header.bindBox')}</Text>
              </Pressable>
            )}

            {/* 分配包厢（仅管理员可见） */}
            {adminInfo && (
              <>
                <View className="h-6 w-[1px] bg-white/30" />
                <Pressable
                  className="flex-row items-center gap-2"
                  onPress={() => router.push('/assign')}
                >
                  <SymbolView name="doc.text" size={20} tintColor="white" />
                  <Text className="text-white font-bold text-sm">{t('header.boxAllocation')}</Text>
                </Pressable>
              </>
            )}

            <View className="h-6 w-[1px] bg-white/30" />

            {/* 语言切换 */}
            <Pressable className="flex-row items-center gap-2" onPress={toggleLanguage}>
              <SymbolView name="globe" size={20} tintColor="white" />
              <Text className="text-white font-bold text-sm">{t('header.languageSwitch')}</Text>
            </Pressable>
          </View>
        </View>

        {/* 主内容：登录区域 */}
        <View className="flex-1 justify-center items-center px-4">
          <View className="w-full max-w-md bg-[#EDE2E1]/90 backdrop-blur-md rounded-3xl p-8 border border-[#D4AF37]/30">
            <Text className="text-gray-800 text-3xl font-serif text-center mb-6">{t('login.guestLogin')}</Text>

            {/* 会员登录 */}
            <View className="mb-4">
              <Text className="text-gray-600 text-sm mb-1 ml-1">{t('login.phoneNumber')}</Text>
              <View className="flex-row items-center bg-white/80 rounded-xl border border-amber-200/60 px-4">
                <SymbolView name="phone" size={18} tintColor="#9CA3AF" />
                <TextInput
                  className="flex-1 py-3 px-2 text-gray-800"
                  placeholder={t('login.pleaseEnter')+t('login.phoneNumber')}
                  placeholderTextColor="#9CA3AF"
                  value={phone}
                  onChangeText={setPhone}
                  keyboardType="phone-pad"
                  editable={!isLoading}
                />
              </View>
            </View>

            <View className="flex-row gap-3 mb-6">
              <TouchableOpacity
                className={`flex-1 py-3 rounded-xl bg-[#B22222] items-center ${isLoading ? 'opacity-70' : ''}`}
                onPress={handleMemberLogin}
                disabled={isLoading}
              >
                {isLoading ? (
                  <ActivityIndicator size="small" color="white" />
                ) : (
                  <Text className="text-white font-bold">{t('login.memberLogin')}</Text>
                )}
              </TouchableOpacity>
              <TouchableOpacity
                className="py-3 px-4 rounded-xl bg-gray-400 items-center justify-center"
                onPress={() => setShowRegister(true)}
                disabled={isLoading}
              >
                <Text className="text-white font-bold">{t('login.register')}</Text>
              </TouchableOpacity>
            </View>

            {/* 游客登录 */}
            <TouchableOpacity
              className={`w-full py-3 rounded-xl bg-[#D4AF37] items-center mb-2 ${isLoading ? 'opacity-70' : ''}`}
              onPress={handleGuestLogin}
              disabled={isLoading}
            >
              <Text className="text-[#801212] font-bold">{t('login.touristLogin')}</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* 注册模态框 */}
        <Modal
          animationType="slide"
          transparent={true}
          visible={showRegister}
          onRequestClose={() => setShowRegister(false)}
        >
          <View className="flex-1 justify-center items-center bg-black/50">
            <View className="w-5/6 bg-[#EDE2E1] rounded-3xl p-6">
              <Text className="text-gray-800 text-2xl font-serif text-center mb-4">{t('login.registerMember')}</Text>

              <Text className="text-gray-600 text-sm mb-1 ml-1">{t('login.userName')}</Text>
              <TextInput
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-3"
                placeholder={t('login.pleaseEnter')+t('login.userName')}
                value={registerParams.username}
                onChangeText={(text) => setRegisterParams({ ...registerParams, username: text })}
              />

              <Text className="text-gray-600 text-sm mb-1 ml-1">{t('login.phoneNumber')}</Text>
              <TextInput
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-3"
                placeholder={t('login.pleaseEnter')+t('login.phoneNumber')}
                keyboardType="phone-pad"
                value={registerParams.phone}
                onChangeText={(text) => setRegisterParams({ ...registerParams, phone: text })}
              />

              <View className="flex-row justify-end gap-3">
                <TouchableOpacity
                  className="px-5 py-3 rounded-xl bg-gray-300"
                  onPress={() => {
                    setShowRegister(false);
                    setRegisterParams({ phone: '', role: 1, username: '' });
                  }}
                >
                  <Text className="text-gray-700 font-bold">{t('common.cancel')}</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  className="px-5 py-3 rounded-xl bg-[#B22222]"
                  onPress={handleRegister}
                  disabled={isLoading}
                >
                  {isLoading ? (
                    <ActivityIndicator size="small" color="white" />
                  ) : (
                    <Text className="text-white font-bold">{t('login.register')}</Text>
                  )}
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </Modal>

        {/* 选择包厢模态框 */}
        <Modal
          animationType="slide"
          transparent={true}
          visible={showRoomPicker}
          onRequestClose={() => setShowRoomPicker(false)}
        >
          <View className="flex-1 justify-center items-center bg-black/50">
            <View className="w-5/6 max-h-96 bg-[#EDE2E1] rounded-3xl p-4">
              <Text className="text-gray-800 text-xl font-serif text-center mb-3">{t('modal.selectBox')}</Text>
              <FlatList
                data={availableRooms}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => (
                  <TouchableOpacity
                    className="py-3 px-4 border-b border-gray-200 flex-row justify-between"
                    onPress={() => handleBindRoom(item)}
                  >
                    <Text className="text-gray-800 text-base">{item.roomName}</Text>
                    <Text className="text-gray-500">{t('common.accommodate')} {item.capacity} {t('common.people')}</Text>
                  </TouchableOpacity>
                )}
                ListEmptyComponent={
                  <Text className="text-center text-gray-500 py-4">暂无可用包厢</Text>
                }
              />
              <TouchableOpacity
                className="mt-3 py-3 items-center"
                onPress={() => setShowRoomPicker(false)}
              >
                <Text className="text-[#B22222] font-bold">{t('common.cancel')}</Text>
              </TouchableOpacity>
            </View>
          </View>
        </Modal>

        {/* 解绑确认模态框 */}
        <Modal
          transparent={true}
          visible={showUnbindConfirm}
          onRequestClose={() => setShowUnbindConfirm(false)}
        >
          <View className="flex-1 justify-center items-center bg-black/50">
            <View className="w-4/5 bg-[#EDE2E1] rounded-2xl p-6">
              <Text className="text-gray-800 text-lg font-bold text-center mb-2">{t('common.confirm')}</Text>
              <Text className="text-gray-600 text-center mb-6">
                {t('alertMsg.confirmUnbindBox')}({boundRoom?.roomName})
              </Text>
              <View className="flex-row justify-end gap-3">
                <TouchableOpacity
                  className="px-5 py-2 rounded-lg bg-gray-300"
                  onPress={() => setShowUnbindConfirm(false)}
                >
                  <Text className="text-gray-700">{t('common.cancel')}</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  className="px-5 py-2 rounded-lg bg-[#B22222]"
                  onPress={handleUnbindRoom}
                >
                  <Text className="text-white">{t('common.confirm')}</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </Modal>
      </View>
    </ImageBackground>
  );
}