package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.UserDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.UserMapper;
import com.fire.firepalaceorderingsys.pojo.User;
import com.fire.firepalaceorderingsys.service.TokenService;
import com.fire.firepalaceorderingsys.service.UserService;
import com.fire.firepalaceorderingsys.util.JwtUtil;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    private final Random random = new Random();

    /**
     * 注册会员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(UserDTO dto){
        if (userMapper.checkUserExists(dto.getUsername(),dto.getPhone()) >= 1) {
            return Result.error("会员已经存在！");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        userMapper.insert(user);
        log.info("会员注册成功: {}", dto.getUsername());
        
        return Result.success("注册成功");
    }

    /**
     * 登录会员
     */
    @Override
    public LoginVO login(String phone){
        // 查询会员
        User user = userMapper.selectByPhone(phone);
        if (user == null) {
            throw new BusinessException("会员不存在");
        }

        // 生成JWT token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername());

        // 保存token到Redis
        // tokenService.saveToken(user.getId(), token);

        log.info("会员登录成功: {}, token: {}", user.getUsername(), token);

        return new LoginVO(Math.toIntExact(user.getId()), user.getUsername(), user.getPhone(), token);
    }

    /**
     * 游客登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO guestLogin() {
        // 生成4位随机数字
        int randomNumber = 1000 + random.nextInt(9000);
        String phone = "visit-" + randomNumber;
        
        // 检查是否已存在
        User existingUser = userMapper.selectByPhone(phone);
        if (existingUser != null) {
            // 如果已存在，重新生成（最多尝试5次）
            for (int i = 0; i < 5; i++) {
                randomNumber = 1000 + random.nextInt(9000);
                phone = "visit-" + randomNumber;
                existingUser = userMapper.selectByPhone(phone);
                if (existingUser == null) {
                    break;
                }
            }
            // 如果5次后仍然存在，使用时间戳
            if (existingUser != null) {
                phone = "visit-" + System.currentTimeMillis() % 10000;
            }
        }

        User guestUser = new User();
        guestUser.setUsername("游客" + randomNumber);
        guestUser.setPhone(phone);
        guestUser.setRole(0);
        guestUser.setCreatedAt(LocalDateTime.now());

        userMapper.insert(guestUser);
        
        // 生成JWT token
        String token = JwtUtil.generateToken(guestUser.getId(), guestUser.getUsername());

        // 保存token到Redis
        // tokenService.saveToken(guestUser.getId(), token);

        log.info("游客登录成功: phone={}, userId={}, token={}", phone, guestUser.getId(), token);

        return new LoginVO(Math.toIntExact(guestUser.getId()), guestUser.getUsername(), phone, token);
    }

    /**
     * 退出登录 (待完善redis配置)
     */
    @Override
    public void logout(Long userId) {
        // 从Redis中删除token
        // tokenService.removeToken(userId);
        log.info("用户退出登录: userId={}", userId);
    }
}
