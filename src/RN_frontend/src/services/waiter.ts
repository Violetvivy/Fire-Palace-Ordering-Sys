import apiClient from './apiClient';
export interface waiterInfoRes{
  code: number;
  msg: string;
  data: waiterInfo
}
export interface allWaiterInfoRes{
  code: number;
  msg: string;
  data: waiterInfo[]
}
export interface waiterInfo{
  id: number,
  waitername: string,
  phone: string,
  workNo: string,
  createdAt: string,
  deletedAt: string | null
}
export interface addWaiter{
  waitername: string,   // 服务员姓名（必填）
  phone: string,        // 手机号（必填，11位数字）
  workNo: string        // 工号（必填）
}
export interface updateWaiter{
  id?: number,
  waitername: string,
  phone: string,        
  workNo: string        
}
export interface deleteWaiterRes{
  code: number;
  msg: string;
  data: null;
}
// 根据id获取服务员信息
export const getWaiterInfo = async (id: number):Promise<waiterInfoRes> => {
  const response = await apiClient.get<waiterInfoRes>(`/waiter/select/${id}`);
  return response.data;
}
// 根据工号获取服务员信息
export const getWaiterInfoByWorkNo = async (workNo: string):Promise<waiterInfoRes> => {
  const response = await apiClient.get<waiterInfoRes>(`/waiter/workNo/${workNo}`);
  return response.data;
}
// 获取所有服务员信息
export const getAllWaiterInfo = async ():Promise<allWaiterInfoRes> => {
  const response = await apiClient.get<allWaiterInfoRes>(`/waiter/list`);
  return response.data;
}
// 根据姓名模糊查询服务员
export const searchWaiterByName = async (name: string):Promise<allWaiterInfoRes> => {
  const response = await apiClient.get<allWaiterInfoRes>(`/waiter/search/${name}`);
  return response.data;
}
// 创建服务员信息
export const createWaiterInfo = async (waiterInfo: addWaiter):Promise<waiterInfoRes> => {
  const response = await apiClient.post<waiterInfoRes>(`/waiter/add`, waiterInfo);
  return response.data;
}
// 更新服务员信息
export const updateWaiterInfo = async (waiterInfo: updateWaiter):Promise<waiterInfoRes> => {
  const response = await apiClient.put<waiterInfoRes>(`/waiter/update`, waiterInfo);
  return response.data;
}
// 删除服务员信息
export const deleteWaiterInfo = async (id: number):Promise<deleteWaiterRes> => {
  const response = await apiClient.delete<deleteWaiterRes>(`/waiter/delete/${id}`);
  return response.data;
}