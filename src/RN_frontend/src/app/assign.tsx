import useAdminStore from '@/stores/useAdminStore';
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
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';

// 引入服务接口
import { createAssignInfo, deleteAssignInfo, getAllAssignInfos } from '@/services/assignInfo';
import {
  addRoom,
  addRoomParams,
  deleteRoom,
  getAllRoom,
  roomInfo,
  updateRoom,
  updateRoomParams,
} from '@/services/room';
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
  const { adminInfo, logout: authLogout } = useAdminStore();

  // ========== 公共数据 ==========
  const [rooms, setRooms] = useState<roomInfo[]>([]);
  const [waiters, setWaiters] = useState<waiterInfo[]>([]);
  const [assignments, setAssignments] = useState<AssignmentDisplay[]>([]);
  const [loading, setLoading] = useState(true);

  // ========== 包厢管理相关 ==========
  const [roomModalVisible, setRoomModalVisible] = useState(false);
  const [editingRoom, setEditingRoom] = useState<roomInfo | null>(null); // 不为空时为编辑模式
  const [roomForm, setRoomForm] = useState<addRoomParams>({
    roomName: '',
    capacity: 0,
    minConsume: 0,
    status: 0,
  });

  // ========== 分配管理相关 ==========
  const [addModalVisible, setAddModalVisible] = useState(false);
  const [selectedRoomId, setSelectedRoomId] = useState<number | null>(null);
  const [selectedWaiterId, setSelectedWaiterId] = useState<number | null>(null);
  const [selectedRoomName, setSelectedRoomName] = useState<string>('');
  const [selectedWaiterName, setSelectedWaiterName] = useState<string>('');

  // 选择器模态框控制
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

      // 处理房间列表
      if (roomsRes.code === 1) {
        setRooms(roomsRes.data || []);
      } else {
        Alert.alert('提示', roomsRes.msg || '获取包厢列表失败');
      }

      // 处理服务员列表
      if (waitersRes.code === 1) {
        setWaiters(waitersRes.data || []);
      } else {
        Alert.alert('提示', waitersRes.msg || '获取服务员列表失败');
      }

      // 处理分配列表
      if (assignRes.code === 1) {
        const assignData = assignRes.data || [];
        const roomMap = new Map<number, roomInfo>();
        const waiterMap = new Map<number, waiterInfo>();

        roomsRes.data?.forEach((r) => roomMap.set(r.id, r));
        waitersRes.data?.forEach((w) => waiterMap.set(w.id, w));

        const displayList: AssignmentDisplay[] = assignData
          .map((item) => {
            const room = roomMap.get(item.roomId);
            const waiter = waiterMap.get(item.waiterId);
            if (!room || !waiter) return null;
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

  // ========== 包厢管理函数 ==========
  const openAddRoomModal = () => {
    setEditingRoom(null);
    setRoomForm({ roomName: '', capacity: 0, minConsume: 0, status: 0 });
    setRoomModalVisible(true);
  };

  const openEditRoomModal = (room: roomInfo) => {
    setEditingRoom(room);
    setRoomForm({
      roomName: room.roomName,
      capacity: room.capacity,
      minConsume: room.minConsume,
      status: room.status,
    });
    setRoomModalVisible(true);
  };

  const handleRoomSubmit = async () => {
    if (!roomForm.roomName.trim() || roomForm.capacity <= 0 || roomForm.minConsume < 0) {
      Alert.alert('提示', '请填写完整的包厢信息（名称、容纳人数、最低消费）');
      return;
    }

    try {
      let res;
      if (editingRoom) {
        // 更新包厢
        const params: updateRoomParams = {
          id: editingRoom.id,
          ...roomForm,
        };
        res = await updateRoom(params);
      } else {
        // 添加包厢
        res = await addRoom(roomForm);
      }

      if (res.code === 1) {
        Alert.alert('成功', editingRoom ? '更新成功' : '添加成功');
        setRoomModalVisible(false);
        fetchAllData(); // 刷新列表
      } else {
        Alert.alert('失败', res.msg || '操作失败');
      }
    } catch (error) {
      Alert.alert('错误', '网络请求失败，请检查网络');
    }
  };

  const handleDeleteRoom = (id: number) => {
    Alert.alert('确认删除', '确定要删除这个包厢吗？', [
      { text: '取消', style: 'cancel' },
      {
        text: '删除',
        style: 'destructive',
        onPress: async () => {
          try {
            const res = await deleteRoom(id);
            if (res.code === 1) {
              Alert.alert('成功', '删除成功');
              fetchAllData();
            } else {
              Alert.alert('失败', res.msg || '删除失败');
            }
          } catch (error) {
            Alert.alert('错误', '网络请求失败，请检查网络');
          }
        },
      },
    ]);
  };

  // ========== 分配管理函数 ==========
  const getAvailableRooms = () => {
    const assignedRoomIds = new Set(assignments.map((a) => a.roomId));
    return rooms.filter((r) => !assignedRoomIds.has(r.id));
  };

  const getAvailableWaiters = () => {
    const assignedWaiterIds = new Set(assignments.map((a) => a.waiterId));
    return waiters.filter((w) => !assignedWaiterIds.has(w.id));
  };

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

  const resetAddForm = () => {
    setSelectedRoomId(null);
    setSelectedWaiterId(null);
    setSelectedRoomName('');
    setSelectedWaiterName('');
  };

  const handleAddAssign = async () => {
    if (!selectedRoomId || !selectedWaiterId) {
      Alert.alert('提示', '请选择包厢和服务员');
      return;
    }

    const roomTaken = assignments.some((a) => a.roomId === selectedRoomId);
    const waiterTaken = assignments.some((a) => a.waiterId === selectedWaiterId);
    if (roomTaken || waiterTaken) {
      Alert.alert('提示', '包厢或服务员已被分配，请重新选择');
      fetchAllData();
      setAddModalVisible(false);
      resetAddForm();
      return;
    }

    try {
      const response = await createAssignInfo({
        adminId: adminInfo!.adminId,
        waiterId: selectedWaiterId,
        roomId: selectedRoomId,
      });
      if (response.code === 1) {
        Alert.alert('成功', '分配成功');
        setAddModalVisible(false);
        resetAddForm();
        fetchAllData();
      } else {
        Alert.alert('失败', response.msg || '创建分配失败');
      }
    } catch (error) {
      Alert.alert('错误', '网络请求失败，请检查网络');
    }
  };

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
              fetchAllData();
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
  };

  // 渲染左侧：包厢管理
  const renderRoomManagement = () => (
    <View className="flex-1 pr-2">
      <View className="flex-row justify-between items-center mb-3">
        <Text className="text-white text-lg font-bold">包厢管理</Text>
        <TouchableOpacity
          className="bg-[#B22222] px-3 py-1.5 rounded-full flex-row items-center"
          onPress={openAddRoomModal}
        >
          <Ionicons name="add" size={18} color="white" />
          <Text className="text-white ml-1 font-bold">新增包厢</Text>
        </TouchableOpacity>
      </View>

      {loading ? (
        <ActivityIndicator size="small" color={COLORS.gold} />
      ) : (
        <FlatList
          data={rooms}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <View className="bg-white/90 rounded-xl p-3 mb-2 border border-amber-200/60">
              <View className="flex-row justify-between">
                <Text className="text-gray-800 font-bold text-base">{item.roomName}</Text>
                <View className="flex-row gap-2">
                  <TouchableOpacity onPress={() => openEditRoomModal(item)}>
                    <Ionicons name="create-outline" size={20} color={COLORS.red} />
                  </TouchableOpacity>
                  <TouchableOpacity onPress={() => handleDeleteRoom(item.id)}>
                    <Ionicons name="trash-outline" size={20} color={COLORS.red} />
                  </TouchableOpacity>
                </View>
              </View>
              <View className="flex-row mt-1">
                <Text className="text-gray-600 text-xs">容纳：{item.capacity}人</Text>
                <Text className="text-gray-600 text-xs ml-3">最低消费：¥{item.minConsume}</Text>
                <View
                  className={`ml-3 px-2 py-0.5 rounded-full ${item.status === 0 ? 'bg-green-200' : 'bg-yellow-200'
                    }`}
                >
                  <Text className="text-xs">{item.status === 0 ? '未绑定' : '已绑定'}</Text>
                </View>
              </View>
            </View>
          )}
          ListEmptyComponent={
            <Text className="text-white/80 text-center py-4">暂无包厢，请添加</Text>
          }
          showsVerticalScrollIndicator={false}
        />
      )}
    </View>
  );

  // 渲染右侧：分配管理
  const renderAssignmentManagement = () => (
    <View className="flex-1 pl-2">
      <View className="flex-row justify-between items-center mb-3">
        <Text className="text-white text-lg font-bold">分配管理</Text>
        <TouchableOpacity
          className="bg-[#B22222] px-3 py-1.5 rounded-full flex-row items-center"
          onPress={() => setAddModalVisible(true)}
        >
          <Ionicons name="add" size={18} color="white" />
          <Text className="text-white ml-1 font-bold">新建分配</Text>
        </TouchableOpacity>
      </View>

      {loading ? (
        <ActivityIndicator size="small" color={COLORS.gold} />
      ) : (
        <FlatList
          data={assignments}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <View className="bg-white/90 rounded-xl p-3 mb-2 border border-amber-200/60">
              <View className="flex-row justify-between">
                <Text className="text-gray-800 font-bold">{item.roomName}</Text>
                <TouchableOpacity onPress={() => handleDeleteAssign(item.id)}>
                  <Ionicons name="trash-outline" size={20} color={COLORS.red} />
                </TouchableOpacity>
              </View>
              <Text className="text-gray-600 text-xs mt-1">
                服务员：{item.waiterName} (工号：{item.workNo})
              </Text>
            </View>
          )}
          ListEmptyComponent={
            <Text className="text-white/80 text-center py-4">暂无分配记录</Text>
          }
          showsVerticalScrollIndicator={false}
        />
      )}
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
            <Image
              source={require('../assets/images/logo.png')}
              className="h-8 w-24"
              resizeMode="contain"
            />
            <Text className="text-white/80 text-xs ml-2">| 后台管理</Text>
          </View>

          <View className="flex-row items-center gap-6">
            {/* 开始点餐按钮 - 醒目放大 */}
            <TouchableOpacity
              className="bg-[#D4AF37] px-5 py-2 rounded-full flex-row items-center"
              onPress={startOrder}
            >
              <Ionicons name="restaurant-outline" size={22} color={COLORS.darkRed} />
              <Text className="text-[#801212] font-bold text-base ml-2">开始点餐</Text>
            </TouchableOpacity>

            <View className="flex-row items-center gap-2">
              <Ionicons name="person-circle-outline" size={24} color="white" />
              <Text className="text-white font-bold text-sm">{adminInfo?.adminname || '管理员'}</Text>
            </View>
            <TouchableOpacity className="flex-row items-center gap-2" onPress={handleLogout}>
              <Ionicons name="exit-outline" size={24} color="white" />
              <Text className="text-white font-bold text-sm">退出</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* 左右分栏内容 */}
        <View className="flex-1 flex-row px-4 py-4">
          {/* 左侧：包厢管理 */}
          {renderRoomManagement()}

          {/* 分隔线 */}
          <View className="w-px bg-white/30 mx-2" />

          {/* 右侧：分配管理 */}
          {renderAssignmentManagement()}
        </View>

        {/* ========== 包厢添加/编辑模态框 ========== */}
        <Modal
          animationType="slide"
          transparent={true}
          visible={roomModalVisible}
          onRequestClose={() => setRoomModalVisible(false)}
        >
          <View className="flex-1 justify-center items-center bg-black/50">
            <View className="w-5/6 bg-[#EDE2E1] rounded-3xl p-6">
              <Text className="text-gray-800 text-2xl font-serif text-center mb-4">
                {editingRoom ? '编辑包厢' : '新增包厢'}
              </Text>

              <Text className="text-gray-600 text-sm mb-1 ml-1">包厢名称</Text>
              <TextInput
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-3"
                placeholder="请输入包厢名称"
                value={roomForm.roomName}
                onChangeText={(text) => setRoomForm({ ...roomForm, roomName: text })}
              />

              <Text className="text-gray-600 text-sm mb-1 ml-1">容纳人数</Text>
              <TextInput
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-3"
                placeholder="请输入容纳人数"
                keyboardType="numeric"
                value={roomForm.capacity.toString()}
                onChangeText={(text) => setRoomForm({ ...roomForm, capacity: parseInt(text) || 0 })}
              />

              <Text className="text-gray-600 text-sm mb-1 ml-1">最低消费</Text>
              <TextInput
                className="bg-white/80 rounded-xl border border-amber-200/60 px-4 py-3 mb-3"
                placeholder="请输入最低消费"
                keyboardType="numeric"
                value={roomForm.minConsume.toString()}
                onChangeText={(text) =>
                  setRoomForm({ ...roomForm, minConsume: parseFloat(text) || 0 })
                }
              />

              <Text className="text-gray-600 text-sm mb-1 ml-1">状态</Text>
              <View className="flex-row gap-4 mb-6">
                <TouchableOpacity
                  className={`px-4 py-2 rounded-full ${roomForm.status === 0 ? 'bg-green-500' : 'bg-gray-300'
                    }`}
                  onPress={() => setRoomForm({ ...roomForm, status: 0 })}
                >
                  <Text className={roomForm.status === 0 ? 'text-white' : 'text-gray-700'}>未绑定</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  className={`px-4 py-2 rounded-full ${roomForm.status === 1 ? 'bg-yellow-500' : 'bg-gray-300'
                    }`}
                  onPress={() => setRoomForm({ ...roomForm, status: 1 })}
                >
                  <Text className={roomForm.status === 1 ? 'text-white' : 'text-gray-700'}>已绑定</Text>
                </TouchableOpacity>
              </View>

              <View className="flex-row justify-end gap-3">
                <TouchableOpacity
                  className="px-5 py-3 rounded-xl bg-gray-300"
                  onPress={() => setRoomModalVisible(false)}
                >
                  <Text className="text-gray-700 font-bold">取消</Text>
                </TouchableOpacity>
                <TouchableOpacity
                  className="px-5 py-3 rounded-xl bg-[#B22222]"
                  onPress={handleRoomSubmit}
                >
                  <Text className="text-white font-bold">确认</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </Modal>

        {/* ========== 添加分配模态框（保持不变） ========== */}
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

        {/* 选择器模态框（保持不变） */}
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