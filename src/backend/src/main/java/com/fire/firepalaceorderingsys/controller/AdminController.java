package com.fire.firepalaceorderingsys.controller;


import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.AdminDTO;
import com.fire.firepalaceorderingsys.service.AdminService;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Result delete(@Valid @RequestBody AdminDTO dto) {
        LoginVO loginVO = adminService.login(dto);
        return Result.success(loginVO);
    }

    /**
     * 删除会员
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam String phone) {
        return adminService.delete(phone);
    }
}
