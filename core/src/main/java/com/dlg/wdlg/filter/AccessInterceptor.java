package com.dlg.wdlg.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头 token
        String token = request.getHeader("token");
        // 2. 检查 token
        if (token == null || !token.equals("YOUR_SECRET_TOKEN")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: token missing or invalid");
            return false; // 阻止请求继续
        }
        // 3. token 有效，放行
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
