import AsyncStorage from '@react-native-async-storage/async-storage';
import { Tabs } from 'expo-router';
import { SymbolView } from 'expo-symbols';
import React from 'react';
import { useTranslation } from 'react-i18next'; // 新增导入
import { Image, Pressable, Text, View } from 'react-native';

import { useColorScheme } from '@/components/useColorScheme';

function CustomHeader() {
  const { t, i18n } = useTranslation(); // 使用 useTranslation

  const toggleLanguage = async () => {
    const newLang = i18n.language === 'en' ? 'zh' : 'en';
    await i18n.changeLanguage(newLang);
    await AsyncStorage.setItem('user-language', newLang);
  };

  return (
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

        {/* 用餐人数 */}
        <View className="flex-row items-center gap-2">
          <SymbolView
            name={{
              ios: 'person.3',
              android: 'people',
              web: 'people',
            }}
            size={20}
            tintColor="white"
          />
          <Text className="text-white font-bold">{t('header.peopleCount')}</Text>
        </View>

        <View className="h-8 w-[1px] bg-white/30" />

        {/* 结束用餐 */}
        <Pressable className="flex-row items-center gap-2" onPress={() => {}}>
          <SymbolView
            name={{
              ios: 'doc.text',
              android:'message',
              web: 'message',
            }}
            size={20}
            tintColor="white"
          />
          <Text className="text-white font-bold text-sm">{t('header.endTheMeal')}</Text>
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
  );
}

export default function TabLayout() {
  const colorScheme = useColorScheme();
  const { t } = useTranslation(); // 用于标签页标题

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: '#801212',
        tabBarStyle: {
          height: 64,
          backgroundColor: '#ffffff',
          borderTopWidth: 1,
          borderTopColor: '#e5e5e5',
        },
        tabBarLabelStyle: {
          fontSize: 10,
          fontWeight: '500',
          marginBottom: 4,
        },
        tabBarIconStyle: {
          marginTop: 4,
        },
        header: (props) => <CustomHeader />,
        headerShown: true,
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: t('tabs.menu'), // 使用翻译
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{ ios: 'list.bullet', android: 'menu', web: 'menu' }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />

      <Tabs.Screen
        name="ai_recommend"
        options={{
          title: t('tabs.recommend'),
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{ ios: 'hand.thumbsup', android: 'thumb_up', web: 'thumb_up' }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />

      <Tabs.Screen
        name="cart"
        options={{
          title: t('tabs.cart'),
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{ ios: 'cart', android: 'shopping_cart', web: 'shopping_cart' }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />

      <Tabs.Screen
        name="service"
        options={{
          title: t('tabs.service'),
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{ ios: 'headphones', android: 'headset', web: 'headset' }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />
    </Tabs>
  );
}