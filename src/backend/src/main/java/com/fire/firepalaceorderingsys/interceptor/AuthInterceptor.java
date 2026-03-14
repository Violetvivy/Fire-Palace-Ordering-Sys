package com.fire.firepalaceorderingsys.interceptor;

import com.fire.firepalaceorderingsys.service.TokenService;
import com.fire.firepalaceorderingsys.util.JwtUtil;
import com.fire.firepalaceorderingsys.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 认证拦截器
 * 面向接口编程：依赖JWT服务接口
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从请求头中获取token
        String token = request.getHeader("token");

        log.info("请求路径: {}, token: {}", request.getRequestURI(), token);

        if (StringUtils.hasText(token)) {
            // 验证token
            if (JwtUtil.validateToken(token)) {
                // token有效，将用户信息存入request和UserContext
                Long userId = JwtUtil.getUserIdFromToken(token);
                String username = JwtUtil.getUsernameFromToken(token);

                // 设置到request属性中
                request.setAttribute("userId", userId);
                request.setAttribute("username", username);

                // 同时设置到UserContext中，方便在Service层获取
                UserContext.setCurrentUser(userId, username);
                log.info("已设置用户上下文，用户ID: {}, 用户名: {}", userId, username);

                return true;
            }
        }

        // token无效或不存在
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"未登录或登录已过期\"}");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求处理完成后，清除用户上下文，避免内存泄漏
        UserContext.clear();
        log.info("请求处理完成，已清除用户上下文");
    }
}
