package com.fire.firepalaceorderingsys.service;

public interface TokenService {

    /**
     * 保存token
     */
    void saveToken(Long userId, String token);

    /**
     * 验证token是否有效
     */
    boolean validateToken(Long userId, String token);

    /**
     * 删除token（退出登录）
     */
    void removeToken(Long userId);
}