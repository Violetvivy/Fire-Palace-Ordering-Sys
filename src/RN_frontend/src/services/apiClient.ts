import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

// 根据环境变量或常量配置基础 URL
const BASE_URL = 'http://localhost:8080'; // 根据实际部署修改

const apiClient = axios.create({
    baseURL: BASE_URL,
    timeout: 10000,
    headers: { 'Content-Type': 'application/json' },
});

// 请求拦截器：从 AsyncStorage 获取 token 并附加到请求头
apiClient.interceptors.request.use(
    async (config: any) => {
        try {
            const token = await AsyncStorage.getItem('adminToken');
            if (token && config.headers) {
                config.headers.Authorization = `Bearer ${token}`;
            }
        } catch (error) {
            console.error('Failed to get token from AsyncStorage', error);
        }
        return config;
    },
    (error: any) => Promise.reject(error)
);

// 响应拦截器：统一处理错误（如 token 过期）
apiClient.interceptors.response.use(
    (response: any) => response,
    (error: any) => {
        // 可以在这里处理 401 等错误
        return Promise.reject(error);
    }
);

export default apiClient;
