import AsyncStorage from '@react-native-async-storage/async-storage';
import { create } from 'zustand';
import { createJSONStorage, persist } from 'zustand/middleware';

interface AdminInfo {
  adminId: number;
  adminname: string;
  token: string;
  phone: string;
}

interface adminState {
  adminInfo: AdminInfo | null;
  isAuthenticated: boolean;
  setAdminInfo: (info: AdminInfo | null) => void;
  logout: () => void;
}

const useAdminStore = create<adminState>()(
  persist(
    (set) => ({
      adminInfo: null,
      isAuthenticated: false,
      setAdminInfo: (info) => set({ adminInfo: info, isAuthenticated: !!info }),
      logout: () => set({ adminInfo: null, isAuthenticated: false }),
    }),
    {
      name: 'admin-storage', // 存储的 key
      storage: createJSONStorage(() => AsyncStorage),
    }
  )
);

export default useAdminStore;