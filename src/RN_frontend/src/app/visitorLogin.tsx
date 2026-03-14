import AsyncStorage from '@react-native-async-storage/async-storage';
import { useRouter } from 'expo-router';
import { StatusBar as ExpoStatusBar } from 'expo-status-bar';
import { SymbolView } from 'expo-symbols';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';

import {
  Image,
  ImageBackground,
  Pressable,
  Text,
  View
} from 'react-native';

const COLORS = {
  red: '#B22222',
  gold: '#D4AF37',
  darkRed: '#801212',
};

export default function AdminLoginScreen() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(false);
  const { t, i18n } = useTranslation(); // 使用 useTranslation
  const toggleLanguage = async () => {
    const newLang = i18n.language === 'en' ? 'zh' : 'en';
    await i18n.changeLanguage(newLang);
    await AsyncStorage.setItem('user-language', newLang);
  };
  return (
    <ImageBackground
      source={{ uri: 'https://img.shetu66.com/2023/12/16/1702700953848478.jpg' }}
      className="flex-1"
      resizeMode="cover"
    >
      <View className="absolute inset-0 bg-black/30" />
      <View className="absolute inset-0 opacity-20 bg-pattern" />

      <View className="flex-1">
        <ExpoStatusBar style="light" />

        <View className="h-16 bg-[#801212] flex-row items-center justify-between px-6">
              <View className="flex-row items-center gap-3">
                <View className="flex-row items-center">
                  <Image
                    source={require('@/assets/images/logo.png')}
                    className="h-8 w-24"
                    resizeMode="contain"
                  />
                  <Text className="text-white text-sm ml-2 opacity-80">| {t('header.systemName')}</Text>
                </View>
              </View>
        
              <View className="flex-row items-center gap-6">
                {/* 当前包厢 */}
                <View className="items-end">
                  <Text className="text-white text-xs opacity-70">{t('header.currentBox')}</Text>
                  <Text className="text-white font-bold">{t('header.boxName')}</Text>
                </View>
        
                <View className="h-8 w-[1px] bg-white/30" />
        
                {/* 包厢分配 */}
                <Pressable className="flex-row items-center gap-2" onPress={() => {}}>
                  <SymbolView
                    name={{
                      ios: 'doc.text',
                      android:'box',
                      web: 'box',
                    }}
                    size={20}
                    tintColor="white"
                  />
                  <Text className="text-white font-bold text-sm">{t('header.boxAllocation')}</Text>
                </Pressable>
                
                <View className="h-8 w-[1px] bg-white/30" />
        
                {/* 中英文切换按钮 */}
                <Pressable className="flex-row items-center gap-2" onPress={toggleLanguage}>
                  <SymbolView
                    name={{
                      ios: 'globe',
                      android: 'translate',
                      web: 'translate',
                    }}
                    size={20}
                    tintColor="white"
                  />
                  <Text className="text-white font-bold text-sm">{t('header.languageSwitch')}</Text>
                </Pressable>
              </View>
            </View>

      </View>
    </ImageBackground>
  );
}