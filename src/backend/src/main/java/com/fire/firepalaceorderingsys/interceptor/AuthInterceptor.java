package com.fire.firepalaceorderingsys.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 认证拦截器
 * 面向接口编程：依赖JWT服务接口
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
}
