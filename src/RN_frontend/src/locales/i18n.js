import AsyncStorage from '@react-native-async-storage/async-storage';
import * as Localization from 'expo-localization';
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import en from './en.json';
import zh from './zh.json';

const resources = {
  en: { translation: en },
  zh: { translation: zh },
};

const initI18n = async () => {
  try {
    let savedLanguage = await AsyncStorage.getItem('user-language');
    if (!savedLanguage) {
      // 安全获取设备语言：如果 locale 不存在则默认为 'en'
      const locale = Localization.locale;
      const deviceLocale = locale ? locale.split('-')[0] : 'en';
      savedLanguage = deviceLocale === 'zh' ? 'zh' : 'en';
    }

    await i18n.use(initReactI18next).init({
      resources,
      lng: savedLanguage,
      fallbackLng: 'en',
      interpolation: { escapeValue: false },
      compatibilityJSON: 'v3',
    });
    console.log('i18n initialized with language:', savedLanguage);
  } catch (error) {
    console.error('i18n initialization failed, falling back to en:', error);
    // 降级初始化
    i18n.use(initReactI18next).init({
      resources,
      lng: 'en',
      fallbackLng: 'en',
      interpolation: { escapeValue: false },
      compatibilityJSON: 'v3',
    });
  }
};

initI18n();

export default i18n;