import AsyncStorage from '@react-native-async-storage/async-storage';
import { create } from 'zustand';
import { createJSONStorage, persist } from 'zustand/middleware';

export interface UserInfo {
  userId: number;
  phone: string;
  token: string;
  type: 'member' | 'guest'; // 区分会员和游客
  username?: string; // 会员可能有用户名，游客无
}

interface UserState {
  userInfo: UserInfo | null;
  isLoggedIn: boolean;
  setUserInfo: (info: UserInfo | null) => void;
  logout: () => void;
}

const useUserStore = create<UserState>()(
  persist(
    (set) => ({
      userInfo: null,
      isLoggedIn: false,
      setUserInfo: (info) => set({ userInfo: info, isLoggedIn: !!info }),
      logout: () => set({ userInfo: null, isLoggedIn: false }),
    }),
    {
      name: 'user-storage',
      storage: createJSONStorage(() => AsyncStorage),
    }
  )
);

export default useUserStore;