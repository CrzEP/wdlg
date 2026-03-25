package com.dlg.wdlg.filter;

import com.dlg.wdlg.config.WdlgValue;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Resource
    WdlgValue wdlgValue;

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
