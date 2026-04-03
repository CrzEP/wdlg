package com.dlg.wdlg.filter;

import com.dlg.wdlg.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头 token
        String token = request.getHeader("token");
        // 2. 检查 token
        if (StringUtils.isEmpty(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: token missing or invalid");
            return false; // 阻止请求继续
        }
        userService.verifyToken(token);
        // 3. token 有效，放行
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
