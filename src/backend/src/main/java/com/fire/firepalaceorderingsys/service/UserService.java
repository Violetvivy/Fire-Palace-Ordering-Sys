package com.fire.firepalaceorderingsys.service;


import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.UserDTO;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 注册会员
     */
    Result register(UserDTO dto);

    /**
     * 登录会员
     */
    LoginVO login(String phone);

    /**
     * 退出登录
     */
    void logout(Long userId);
}
