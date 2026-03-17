import apiClient from '@/services/apiClient';
export interface addRoomParams {
  roomName: string,      // 包厢名称（必填，不能重复）
  capacity: number,            // 容纳人数（必填，正整数）
  minConsume: number,     // 最低消费（必填，正数）
  status: number                // 状态：0-未绑定，1-已绑定（必填）
}
export interface updateRoomParams {
  id: number,   // 包厢ID（必填）
  roomName: string, 
  capacity: number,     
  minConsume: number,  
  status: number                
}
export interface roomInfo{
  id: number,
  roomName: string,
  capacity: number,
  minConsume: number,
  status: number,
  createTime: string
}
export interface getRoomByIdRes{
  code: number,
  msg: string,
  data: roomInfo | null
}
export interface getAllRoomRes{
  code:number;
  msg:string;
  data:roomInfo[]
}
// 添加包厢
export const addRoom = async (params: addRoomParams):Promise<any> => {
  const response = await apiClient.post('/room/add', params);
  return response.data;
}
// 删除包厢
export const deleteRoom = async (id: number):Promise<any> => {
  const response = await apiClient.delete(`/room/delete/${id}`);
  return response.data;
}
// 更新包厢
export const updateRoom = async (params: updateRoomParams):Promise<any> => {
  const response = await apiClient.put('/room/update', params);
  return response.data;
}
// 根据ID查询包厢
export const getRoomById = async (id: number):Promise<getRoomByIdRes> => {
  const response = await apiClient.get<getRoomByIdRes>(`/room/${id}`);
  return response.data;
}
// 获取所有包厢
export const getAllRoom = async ():Promise<getAllRoomRes> => {
  const response = await apiClient.get<getAllRoomRes>('/room/all');
  return response.data;
}
// 根据状态查询包厢是否被绑定
export const getRoomByStatus = async (status: number):Promise<getAllRoomRes> => {
  const response = await apiClient.get<getAllRoomRes>(`/room/status/${status}`);
  return response.data;
}
// 绑定包厢
export const bindRoom = async (roomName: string): Promise<any> => {
  const response = await apiClient.post('/room/binding', null, {
    params: { roomName }
  });
  return response.data;
};

// 解绑包厢
export const unbindRoom = async (roomName: string): Promise<any> => {
  const response = await apiClient.post('/room/unbinding', null, {
    params: { roomName }
  });
  return response.data;
};