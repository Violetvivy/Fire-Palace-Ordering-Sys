import apiClient from './apiClient';

//登录数据
export interface LoginData {
  token: string;
  userId: number;
  phone: string;
}
//用户登录响应
export interface LoginResponse {
  code: number;
  msg: string;
  data: LoginData;
}
//退出登录响应
export interface LogoutResponse {
  code: number;
  msg: string;
  data: string;
}
//注册响应
export interface RegisterResponse {
  code: number;
  msg: string;
  data: null;
}
//用户注册参数
export interface UserRegisterParams {
  phone: string;
  password: string;
  username: string;
}

//注册会员
export const userRegister = async (params: UserRegisterParams): Promise<RegisterResponse> => {
  const response = await apiClient.post<RegisterResponse>('/auth/register', params);
  return response.data;
};

//用户登录
export const userLogin = async (phone: string): Promise<LoginResponse> => {
  const response = await apiClient.post<LoginResponse>('/auth/login', null, {
    params: { phone }
  });
  return response.data;
};

//游客登录
export const guestLogin = async (): Promise<LoginResponse> => {
  const response = await apiClient.get<LoginResponse>('/auth/guest-login');
  return response.data;
};

//退出登录
export const userLogout = async (): Promise<LogoutResponse> => {
  const response = await apiClient.post<LogoutResponse>('/auth/logout');
  return response.data;
};