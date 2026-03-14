import useAuthStore from '@/stores/useAuthStore';
import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import { StatusBar as ExpoStatusBar } from 'expo-status-bar';
import { useEffect, useState } from 'react';
import {
  ActivityIndicator,
  Alert,
  FlatList,
  Image,
  ImageBackground,
  Modal,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

// 引入服务接口
import { createAssignInfo, deleteAssignInfo, getAllAssignInfos } from '@/services/assignInfo';
import { getAllRoom, roomInfo } from '@/services/room';
import { getAllWaiterInfo, waiterInfo } from '@/services/waiter';

const COLORS = {
  red: '#B22222',
  gold: '#D4AF37',
  darkRed: '#801212',
};

// 分配项显示的数据结构
interface AssignmentDisplay {
  id: number;
  roomId: number;
  roomName: string;
  waiterId: number;
  waiterName: string;
  workNo: string;
}

export default function Assign() {
  const router = useRouter();
  const { adminInfo, logout: authLogout } = useAuthStore();

  // 数据状态
  const [assignments, setAssignments] = useState<AssignmentDisplay[]>([]);
  const [rooms, setRooms] = useState<roomInfo[]>([]);
  const [waiters, setWaiters] = useState<waiterInfo[]>([]);
  const [loading, setLoading] = useState(true);

  // 添加分配模态框控制
  const [addModalVisible, setAddModalVisible] = useState(false);
  const [selectedRoomId, setSelectedRoomId] = useState<number | null>(null);
  const [selectedWaiterId, setSelectedWaiterId] = useState<number | null>(null);
  const [selectedRoomName, setSelectedRoomName] = useState<string>('');
  const [selectedWaiterName, setSelectedWaiterName] = useState<string>('');

  // 选择器模态框控制（用于选择包厢或服务员）
  const [pickerVisible, setPickerVisible] = useState(false);
  const [pickerType, setPickerType] = useState<'room' | 'waiter'>('room');
  const [pickerOptions, setPickerOptions] = useState<{ id: number; name: string; extra?: string }[]>([]);

  // 检查认证，未登录则跳转
  useEffect(() => {
    if (!adminInfo) {
      router.replace('/');
    }
  }, [adminInfo]);

  // 加载初始数据
  useEffect(() => {
    fetchAllData();
  }, []);

  const fetchAllData = async () => {
    setLoading(true);
    try {
      const [assignRes, roomsRes, waitersRes] = await Promise.all([
        getAllAssignInfos(),
        getAllRoom(),
        getAllWaiterInfo(),
      ]);

      // 处理分配列表
      if (assignRes.code === 1) {
        const assignData = assignRes.data || [];
        // 构建房间和 waiter 映射
        const roomMap = new Map<number, roomInfo>();
        const waiterMap = new Map<number, waiterInfo>();
        if (roomsRes.code === 1) {
          setRooms(roomsRes.data || []);
          roomsRes.data.forEach((r) => roomMap.set(r.id, r));
        }
        if (waitersRes.code === 1) {
          setWaiters(waitersRes.data || []);
          waitersRes.data.forEach((w) => waiterMap.set(w.id, w));
        }

        // 组装显示数据
        const displayList: AssignmentDisplay[] = assignData
          .map((item) => {
            const room = roomMap.get(item.roomId);
            const waiter = waiterMap.get(item.waiterId);
            if (!room || !waiter) return null; // 如果房间或服务员不存在，忽略
            return {
              id: item.id,
              roomId: item.roomId,
              roomName: room.roomName,
              waiterId: item.waiterId,
              waiterName: waiter.waitername,
              workNo: waiter.workNo,
            };
          })
          .filter((item): item is AssignmentDisplay => item !== null);

        setAssignments(displayList);
      } else {
        Alert.alert('提示', assignRes.msg || '获取分配列表失败');
      }
    } catch (error) {
      Alert.alert('错误', '网络请求失败，请检查网络');
    } finally {
      setLoading(false);
    }
  };

  // 获取可用的包厢（未被分配的）
  const getAvailableRooms = () => {
    const assignedRoomIds = new Set(assignments.map((a) => a.roomId));
    return rooms.filter((r) => !assignedRoomIds.has(r.id));
  };

  // 获取可用的服务员（未被分配的）
  const getAvailableWaiters = () => {
    const assignedWaiterIds = new Set(assignments.map((a) => a.waiterId));
    return waiters.filter((w) => !assignedWaiterIds.has(w.id));
  };

  // 打开选择器
  const openPicker = (type: 'room' | 'waiter') => {
    setPickerType(type);
    if (type === 'room') {
      const options = getAvailableRooms().map((r) => ({ id: r.id, name: r.roomName }));
      setPickerOptions(options);
    } else {
      const options = getAvailableWaiters().map((w) => ({
        id: w.id,
        name: w.waitername,
        extra: w.workNo,
      }));
      setPickerOptions(options);
    }
    setPickerVisible(true);
  };

  // 选择器确认
  const handlePickerSelect = (id: number, name: string, extra?: string) => {
    if (pickerType === 'room') {
      setSelectedRoomId(id);
      setSelectedRoomName(name);
    } else {
      setSelectedWaiterId(id);
      setSelectedWaiterName(extra ? `${name} (${extra})` : name);
    }
    setPickerVisible(false);
  };

  // 重置添加表单
  const resetAddForm = () => {
    setSelectedRoomId(null);
    setSelectedWaiterId(null);
    setSelectedRoomName('');
    setSelectedWaiterName('');
  };

  // 处理添加分配
  const handleAddAssign = async () => {
    if (!selectedRoomId || !selectedWaiterId) {
      Alert.alert('提示', '请选择包厢和服务员');
      return;
    }

    // 再次检查是否已被占用（防止并发）
    const roomTaken = assignments.some((a) => a.roomId === selectedRoomId);
    const waiterTaken = assignments.some((a) => a.waiterId === selectedWaiterId);
    if (roomTaken || waiterTaken) {
      Alert.alert('提示', '包厢或服务员已被分配，请重新选择');
      // 刷新列表并关闭模态框，让用户重新选择
      fetchAllData();
      setAddModalVisible(false);
      resetAddForm();
      return;
    }

    try {
      // alert(adminInfo?.adminId);

      const response = await createAssignInfo({
        adminId: adminInfo!.adminId,
        waiterId: selectedWaiterId,
        roomId: selectedRoomId,
      });
      if (response.code === 1) {
        Alert.alert('成功', '分配成功');
        setAddModalVisible(false);
        resetAddForm();
        fetchAllData(); // 刷新列表
      } else {
        Alert.alert('失败', response.msg || '创建分配失败');
      }
    } catch (error) {
      Alert.alert('错误', '网络请求失败，请检查网络');
    }
  };

  // 处理删除分配
  const handleDeleteAssign = (id: number) => {
    Alert.alert('确认删除', '确定要删除这条分配记录吗？', [
      { text: '取消', style: 'cancel' },
      {
        text: '删除',
        style: 'destructive',
        onPress: async () => {
          try {
            const response = await deleteAssignInfo(id);
            if (response.code === 1) {
              Alert.alert('成功', '删除成功');
              fetchAllData(); // 刷新列表
            } else {
              Alert.alert('失败', response.msg || '删除失败');
            }
          } catch (error) {
            Alert.alert('错误', '网络请求失败，请检查网络');
          }
        },
      },
    ]);
  };

  const handleLogout = async () => {
    authLogout();
    router.replace('/');
  };

  const startOrder = () => {
    router.replace('/visitorLogin');
  }

  // 渲染单个分配项
  const renderItem = ({ item }: { item: AssignmentDisplay }) => (
    <View className="bg-white/90 rounded-xl p-4 mb-3 flex-row justify-between items-center border border-amber-200/60">
      <View className="flex-1">
        <Text className="text-gray-800 text-base font-bold">包厢：{item.roomName}</Text>
        <View className="flex-row items-center mt-1">
          <Ionicons name="person" size={16} color={COLORS.red} />
          <Text className="text-gray-700 ml-1">
            服务员：{item.waiterName} (工号：{item.workNo})
          </Text>
        </View>
      </View>
      <TouchableOpacity onPress={() => handleDeleteAssign(item.id)} className="p-2">
        <Ionicons name="trash-outline" size={24} color={COLORS.red} />
      </TouchableOpacity>
    </View>
  );

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

        {/* 顶部导航 */}
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
              <Text className="text-white font-bold text-sm">{adminInfo?.adminname || '管理员'}</Text>
            </View>
            <TouchableOpacity className="flex-row items-center gap-2" onPress={handleLogout}>
              <Ionicons name="exit-outline" size={24} color="white" />
              <Text className="text-white font-bold text-sm">退出登录</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* 主内容 */}
        <View className="flex-1 px-4 py-4">
          {/* 标题与添加按钮 */}
          <View className="flex-row justify-between items-center mb-4">
            <Text className="text-white text-xl font-bold">分配列表</Text>
            <View className='flex-row items-center gap-6'>
              <TouchableOpacity
                className="bg-[#B22222] px-4 py-2 rounded-full flex-row items-center"
                onPress={() => setAddModalVisible(true)}
              >
                <Ionicons name="add" size={20} color="white" />
                <Text className="text-white ml-1 font-bold">新建分配</Text>
              </TouchableOpacity>

              <TouchableOpacity
                className="bg-[#B22222] px-4 py-2 rounded-full flex-row items-center"
                onPress={startOrder}
              >
                <Ionicons name="star-outline" size={20} color="white" />
                <Text className="text-white ml-1 font-bold">开始点餐</Text>
              </TouchableOpacity>
            </View>
          </View>

          {/* 列表或加载状态 */}
          {loading ? (
            <View className="flex-1 justify-center items-center">
              <ActivityIndicator size="large" color={COLORS.gold} />
            </View>
          ) : (
            <FlatList
              data={assignments}
              renderItem={renderItem}
              keyExtractor={(item) => item.id.toString()}
              showsVerticalScrollIndicator={false}
              contentContainerStyle={{ paddingBottom: 20 }}
              ListEmptyComponent={
                <View className="flex-1 justify-center items-center py-10">
                  <Text className="text-white/80 text-lg">暂无分配记录</Text>
                </View>
              }
            />
          )}
        </View>

        {/* 添加分配模态框 */}
        <Modal
          animationType="slide"
          transparent={true}
          visible={addModalVisible}
          onRequestClose={() => {
            setAddModalVisible(false);
            resetAddForm();
          }}
        >
          <View className="flex-1 justify-center items-center bg-black/50">
            <View className="w-5/6 bg-[#EDE2E1] rounded-3xl p-6">
              <Text className="text-gray-800 text-2xl font-serif text-center mb-4">新建分配</Text>

              {/* 选择包厢 */}
              <Text className="text-gray-600 text-sm mb-1 ml-1">选择包厢</Text>
              <TouchableOpacity
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-4 flex-row justify-between items-center"
                onPress={() => openPicker('room')}
              >
                <Text className={selectedRoomId ? 'text-gray-800' : 'text-gray-400'}>
                  {selectedRoomName || '请选择包厢'}
                </Text>
                <Ionicons name="chevron-down" size={20} color="#9CA3AF" />
              </TouchableOpacity>

              {/* 选择服务员 */}
              <Text className="text-gray-600 text-sm mb-1 ml-1">选择服务员</Text>
              <TouchableOpacity
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-6 flex-row justify-between items-center"
                onPress={() => openPicker('waiter')}
              >
                <Text className={selectedWaiterId ? 'text-gray-800' : 'text-gray-400'}>
                  {selectedWaiterName || '请选择服务员'}
                </Text>
                <Ionicons name="chevron-down" size={20} color="#9CA3AF" />
              </TouchableOpacity>

              {/* 按钮组 */}
              <View className="flex-row justify-end gap-3">
                <TouchableOpacity
                  className="px-5 py-3 rounded-xl bg-gray-300"
                  onPress={() => {
                    setAddModalVisible(false);
                    resetAddForm();
                  }}
                >
                  <Text className="text-gray-700 font-bold">取消</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  className="px-5 py-3 rounded-xl bg-[#B22222]"
                  onPress={handleAddAssign}
                >
                  <Text className="text-white font-bold">确认</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </Modal>

        {/* 选择器模态框（包厢/服务员列表） */}
        <Modal
          animationType="slide"
          transparent={true}
          visible={pickerVisible}
          onRequestClose={() => setPickerVisible(false)}
        >
          <View className="flex-1 justify-center items-center bg-black/50">
            <View className="w-5/6 max-h-96 bg-[#EDE2E1] rounded-3xl p-4">
              <Text className="text-gray-800 text-xl font-serif text-center mb-3">
                {pickerType === 'room' ? '选择包厢' : '选择服务员'}
              </Text>
              <FlatList
                data={pickerOptions}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => (
                  <TouchableOpacity
                    className="py-3 px-4 border-b border-gray-200"
                    onPress={() => handlePickerSelect(item.id, item.name, item.extra)}
                  >
                    <Text className="text-gray-800 text-base">
                      {item.name}
                      {item.extra && <Text className="text-gray-500 text-sm"> ({item.extra})</Text>}
                    </Text>
                  </TouchableOpacity>
                )}
                ListEmptyComponent={
                  <Text className="text-center text-gray-500 py-4">
                    {pickerType === 'room' ? '暂无可用包厢' : '暂无可用服务员'}
                  </Text>
                }
              />
              <TouchableOpacity
                className="mt-3 py-3 items-center"
                onPress={() => setPickerVisible(false)}
              >
                <Text className="text-[#B22222] font-bold">取消</Text>
              </TouchableOpacity>
            </View>
          </View>
        </Modal>
      </View>
    </ImageBackground>
  );
}