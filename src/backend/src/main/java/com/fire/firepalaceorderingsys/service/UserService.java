package com.fire.firepalaceorderingsys.service;


import com.fire.firepalaceorderingsys.dto.UserDTO;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * 注册会员
     */
    void register(UserDTO dto);

    /**
     * 登录会员
     */
    LoginVO login(String phone);
}
