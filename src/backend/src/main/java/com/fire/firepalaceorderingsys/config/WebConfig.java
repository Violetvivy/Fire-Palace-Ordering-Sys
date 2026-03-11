package com.fire.firepalaceorderingsys.config;

import com.fire.firepalaceorderingsys.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 配置拦截器链
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Value("${auth.interceptor.enabled:true}")
    private boolean authInterceptorEnabled;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 根据配置决定是否启用认证拦截器
        if (authInterceptorEnabled) {
            registry.addInterceptor(authInterceptor)
                    // 拦截所有路径
                    .addPathPatterns("/**")
                    // 放行认证相关路径和错误处理路径
                    .excludePathPatterns(
                            "/auth/login",
                            "/auth/register",
                            "/auth/guest-login",
                            "/error",
                            "/admin/login"
                    );
        }
    }
}
