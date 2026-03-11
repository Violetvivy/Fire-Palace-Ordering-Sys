package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // token Key
    public static final String TOKEN_KEY = "token:";
    // token 过期时间
    public static final int TOKEN_EXPIRE_DAYS = 7;

    @Override
    public void saveToken(Long userId, String token) {
        String key = TOKEN_KEY + userId;
        redisTemplate.opsForValue().set(key, token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        log.info("Token已保存到Redis: userId={}", userId);
    }

    @Override
    public boolean validateToken(Long userId, String token) {
        String key = TOKEN_KEY + userId;
        String storedToken = redisTemplate.opsForValue().get(key);

        if (storedToken == null) {
            log.warn("Token不存在: userId={}", userId);
            return false;
        }

        return storedToken.equals(token);
    }

    @Override
    public void removeToken(Long userId) {
        String key = TOKEN_KEY + userId;
        redisTemplate.delete(key);
        log.info("Token已删除: userId={}", userId);
    }
}