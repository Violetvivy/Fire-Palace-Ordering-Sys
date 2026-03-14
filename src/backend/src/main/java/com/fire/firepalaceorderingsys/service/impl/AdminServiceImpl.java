package com.fire.firepalaceorderingsys.service.impl;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.AdminDTO;
import com.fire.firepalaceorderingsys.exception.BusinessException;
import com.fire.firepalaceorderingsys.mapper.AdminMapper;
import com.fire.firepalaceorderingsys.mapper.UserMapper;
import com.fire.firepalaceorderingsys.pojo.Admin;
import com.fire.firepalaceorderingsys.service.AdminService;
import com.fire.firepalaceorderingsys.util.JwtUtil;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     */
    @Override
    public LoginVO login(AdminDTO adminDTO) {
        // 查询管理员
        Admin admin = adminMapper.selectByPhone(adminDTO.getAdminname(), adminDTO.getPhone());
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        if (!admin.getPassword().equals(adminDTO.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成JWT token
        String token = JwtUtil.generateToken(admin.getId(), admin.getAdminname());

        // 保存token到Redis
        // tokenService.saveToken(user.getId(), token);

        log.info("会员登录成功: {}, token: {}", admin.getAdminname(), token);

        return new LoginVO(Math.toIntExact(admin.getId()), admin.getAdminname(), admin.getPhone(), token);
    }

    /**
     * 删除会员
     */
    @Override
    public Result delete(String phone) {
        userMapper.deleteByPhone(phone);
        log.info("用户已删除: phone={}", phone);
        return Result.success("删除成功");
    }
}
