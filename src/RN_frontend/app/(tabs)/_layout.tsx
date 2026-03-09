import { Tabs } from 'expo-router';
import { SymbolView } from 'expo-symbols';
import React from 'react';
import { Image, Pressable, Text, View } from 'react-native';

import { useColorScheme } from '@/components/useColorScheme';

// 完全仿照 HTML 的顶部栏组件
function CustomHeader() {
  return (
    <View className="h-16 bg-[#801212] flex-row items-center justify-between px-6">
      {/* 左侧区域：火宫殿文字 + 包厢说明 */}
      <View className="flex-row items-center gap-3">
        <View className="flex-row items-center">
          <Image
            source={require('@/assets/images/logo.png')}
            className="h-8 w-24"
            resizeMode="contain"
          />
          <Text className="text-white text-sm ml-2 opacity-80">
            | 包厢贵宾点餐系统
          </Text>
        </View>
      </View>

      {/* 右侧区域：包厢信息、人数、语言切换、会员码 */}
      <View className="flex-row items-center gap-6">
        {/* 当前包厢 */}
        <View className="items-end">
          <Text className="text-white text-xs opacity-70">当前包厢：</Text>
          <Text className="text-white font-bold">岳阳楼 (VIP-08)</Text>
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
          <Text className="text-white font-bold">8人用餐</Text>
        </View>

        <View className="h-8 w-[1px] bg-white/30" />

        {/* 中英文切换 */}
        <Pressable className="flex-row items-center gap-2" onPress={() => {}}>
          <SymbolView
            name={{
              ios: 'globe',
              android: 'translate',
              web: 'translate',
            }}
            size={20}
            tintColor="white"
          />
          <Text className="text-white font-bold text-sm">中 / EN</Text>
        </Pressable>

        <View className="h-8 w-[1px] bg-white/30" />

        {/* 会员码 */}
        <Pressable className="flex-row items-center gap-2" onPress={() => {}}>
          <SymbolView
            name={{
              ios: 'doc.text',
              android: 'description',
              web: 'description',
            }}
            size={20}
            tintColor="white"
          />
          <Text className="text-white font-bold text-sm">会员码</Text>
        </Pressable>
      </View>
    </View>
  );
}

export default function TabLayout() {
  const colorScheme = useColorScheme();

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
        // 所有页面使用同一个自定义头部
        header: (props) => <CustomHeader />,
        // 确保头部显示
        headerShown: true,
      }}>
      
      {/* 菜单标签页 */}
      <Tabs.Screen
        name="index"
        options={{
          title: '菜单',
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{
                ios: 'list.bullet',
                android: 'menu',
                web: 'menu',
              }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />

      {/* 推荐标签页 */}
      <Tabs.Screen
        name="ai_recommend"
        options={{
          title: '推荐',
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{
                ios: 'hand.thumbsup',
                android: 'thumb_up',
                web: 'thumb_up',
              }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />

      {/* 购物车标签页 */}
      <Tabs.Screen
        name="cart"
        options={{
          title: '购物车',
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{
                ios: 'cart',
                android: 'shopping_cart',
                web: 'shopping_cart',
              }}
              tintColor={focused ? '#801212' : color}
              size={24}
              type={focused ? 'hierarchical' : 'monochrome'}
            />
          ),
        }}
      />

      {/* 服务标签页 */}
      <Tabs.Screen
        name="service"
        options={{
          title: '服务',
          tabBarIcon: ({ color, focused }) => (
            <SymbolView
              name={{
                ios: 'headphones',
                android: 'headset',
                web: 'headset',
              }}
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