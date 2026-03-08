package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.dto.UserDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.UserMapper;
import com.fire.firepalaceorderingsys.pojo.User;
import com.fire.firepalaceorderingsys.service.UserService;
import com.fire.firepalaceorderingsys.util.JwtUtil;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册会员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserDTO dto){
        if (userMapper.checkUserExists(dto.getUsername(),dto.getPhone())==1) {
            throw new BusinessException("会员已经存在！");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        userMapper.insert(user);
        log.info("会员注册成功: {}", dto.getUsername());
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

        log.info("会员登录成功: {}", user.getUsername());

        return new LoginVO(Math.toIntExact(user.getId()), user.getUsername(), user.getPhone(), token);
    }
}
