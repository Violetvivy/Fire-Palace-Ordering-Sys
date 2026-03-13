import apiClient from './apiClient';

export interface AdminLoginParams {
  adminname: string;
  password: string;
}

export interface AdminLoginResponse {
  code: number;
  msg: string;
  data: {
    token: string;
    adminId: number;
    adminname: string;
  };
}

export const adminLogin = async (params: AdminLoginParams): Promise<AdminLoginResponse> => {
  const response = await apiClient.post<AdminLoginResponse>('/admin/login', params);
  return response.data;
};