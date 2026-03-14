import useAuthStore from '@/stores/useAuthStore'; // 根据实际路径调整
import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

const apiClient = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' },
});

// 请求拦截器：从 store 获取 token
apiClient.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().adminInfo?.token;
    if (token) {
      config.headers.token = token;
    }
    // const adminId = useAuthStore.getState().adminInfo?.adminId;
    // if (adminId) {
    //   config.headers.adminId = adminId;
    // }
    return config;
  },
  (error) => Promise.reject(error)
);

// 响应拦截器：处理 401（token 过期等）
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // token 无效，自动登出
      useAuthStore.getState().logout();
      // 可在此触发全局导航跳转登录页，但需要额外处理（如使用事件总线或导航 ref）
    }
    return Promise.reject(error);
  }
);

export default apiClient;