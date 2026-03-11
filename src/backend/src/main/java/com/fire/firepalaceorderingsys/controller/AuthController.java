package com.fire.firepalaceorderingsys.controller;


import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.UserDTO;
import com.fire.firepalaceorderingsys.service.UserService;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 注册会员
     */
    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserDTO dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestParam("phone") String phone) {
        LoginVO loginVO = userService.login(phone);
        return Result.success(loginVO);
    }

    /**
     * 游客登录
     */
    @GetMapping("/guest-login")
    public Result guestLogin() {
        LoginVO loginVO = userService.guestLogin();
        return Result.success(loginVO);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId != null) {
            userService.logout(userId);
        }
        return Result.success("退出登录成功");
    }
}
