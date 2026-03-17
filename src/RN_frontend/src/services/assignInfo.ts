import apiClient from "@/services/apiClient";

//分配信息数据
export interface AssignInfoData {
    id: number;
    adminId: number;
    waiterId: number;
    roomId: number;
    createdAt: string;
    deletedAt: string | null;
}
//创建分配信息
export interface CreateAssignInfoParams {
    adminId: number;
    waiterId: number;
    roomId: number;
}
//单个分配信息响应
export interface AssignInfoResponse {
    code: number;
    msg: string;
    data: AssignInfoData;
}
//列表分配信息响应
export interface AssignInfoListResponse {
    code: number;
    msg: string;
    data: AssignInfoData[];
}
//删除分配信息响应
export interface DeleteAssignInfoResponse {
    code: number;
    msg: string;
    data: null;
}

//创建分配信息
export const createAssignInfo = async (params: CreateAssignInfoParams): Promise<AssignInfoResponse> => {
    const response = await apiClient.post<AssignInfoResponse>('/assign-info/create', params);
    return response.data;
};
//根据ID获取分配信息
export const getAssignInfoById = async (id: number): Promise<AssignInfoResponse> => {
    const response = await apiClient.get<AssignInfoResponse>(`/assign-info/select/${id}`);
    return response.data;
};
//获取所有分配信息
export const getAllAssignInfos = async (): Promise<AssignInfoListResponse> => {
    const response = await apiClient.get<AssignInfoListResponse>('/assign-info/selectAll');
    return response.data;
};
//根据服务员ID获取分配信息
export const getAssignInfosByWaiterId = async (waiterId: number): Promise<AssignInfoListResponse> => {
    const response = await apiClient.get<AssignInfoListResponse>(`/assign-info/waiter/${waiterId}`);
    return response.data;
};
//根据包厢ID获取分配信息
export const getAssignInfosByRoomId = async (roomId: number): Promise<AssignInfoListResponse> => {
    const response = await apiClient.get<AssignInfoListResponse>(`/assign-info/room/${roomId}`);
    return response.data;
};
//根据管理员ID获取分配信息
export const getAssignInfosByAdminId = async (adminId: number): Promise<AssignInfoListResponse> => {
    const response = await apiClient.get<AssignInfoListResponse>(`/assign-info/admin/${adminId}`);
    return response.data;
};
//根据日期查询分配信息
export const getAssignInfosByDate = async (date: string): Promise<AssignInfoListResponse> => {
    const response = await apiClient.get<AssignInfoListResponse>('/assign-info/date', { params: { date } });
    return response.data;
};
//删除分配信息
export const deleteAssignInfo = async (id: number): Promise<DeleteAssignInfoResponse> => {
    const response = await apiClient.delete<DeleteAssignInfoResponse>(`/assign-info/delete/${id}`);
    return response.data;
};