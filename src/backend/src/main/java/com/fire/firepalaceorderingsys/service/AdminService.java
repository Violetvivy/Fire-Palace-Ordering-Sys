package com.fire.firepalaceorderingsys.service;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.AdminDTO;
import com.fire.firepalaceorderingsys.vo.LoginVO;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    /**
     * 管理员登录
     */
    LoginVO login(AdminDTO adminDTO);

    /**
     * 删除会员
     */
    Result delete(String phone);
}
