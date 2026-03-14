import apiClient from "@/services/apiClient";

// 管理员登录
export interface AdminLoginParams {
    adminname: string;
    password: string;
    phone: string;
}
// 管理员登录响应
export interface AdminLoginResponse {
    code: number;
    msg: string;
    data: {
        token: string;
        userId: number;
        username: string;
    };
}
//删除会员响应
export interface DeleteMemberResponse {
    code: number;
    msg: string;
    data: null; }

// 管理员登录
export const adminLogin = async (params: AdminLoginParams): Promise<AdminLoginResponse> => {
    const response = await apiClient.post<AdminLoginResponse>('/admin/login', params);
    return response.data;
};

//删除会员
export const deleteMember = async (phone: string): Promise<DeleteMemberResponse> => {
    const response = await apiClient.post<DeleteMemberResponse>('/admin/delete', null, { params: { phone } });
    return response.data;
};