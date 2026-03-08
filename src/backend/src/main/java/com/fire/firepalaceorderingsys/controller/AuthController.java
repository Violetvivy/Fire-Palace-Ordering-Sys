package com.fire.firepalaceorderingsys.controller;


import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.UserDTO;
import com.fire.firepalaceorderingsys.service.UserService;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 注册会员
     */
    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserDTO dto) {
        userService.register(dto);
        return Result.success("注册成功");
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestParam("phone") String phone) {
        LoginVO loginVO = userService.login(phone);
        return Result.success(loginVO);
    }
}
