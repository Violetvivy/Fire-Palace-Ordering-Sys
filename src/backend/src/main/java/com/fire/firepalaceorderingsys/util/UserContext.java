package com.fire.firepalaceorderingsys.util;

/**
 * 用户上下文工具类
 * 用于获取当前登录用户的信息
 */
public class UserContext {

    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> usernameThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前用户信息
     */
    public static void setCurrentUser(Long userId, String username) {
        userIdThreadLocal.set(userId);
        usernameThreadLocal.set(username);
    }

    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        return userIdThreadLocal.get();
    }

    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        return usernameThreadLocal.get();
    }

    /**
     * 清除当前用户信息
     */
    public static void clear() {
        userIdThreadLocal.remove();
        usernameThreadLocal.remove();
    }
}