package com.dlg.wdlg.util;

import com.dlg.wdlg.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 获取当前请求的HttpServletRequest对象
 *
 */
public class ServletRequestUtil {

    /**
     *
     * 获取 ServletRequestAttributes
     *
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取非空的请求servlet对象
     *
     * @return req servlet
     */
    public static HttpServletRequest getRequestWithNotNull() {
        ServletRequestAttributes attributes = getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException("request attributes is null");
        }
        return attributes.getRequest();
    }

    /**
     * 获取非空的token
     *
     * @return token
     */
    public static String getTokenWithNotNull() {
        HttpServletRequest request = getRequestWithNotNull();
        String token =  request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException("token not found");
        }
        return token;
    }

    /**
     * 获取非空的响应 servlet 对象
     *
     * @return respond servlet
     */
    public static HttpServletResponse getRespondWithNotNull() {
        ServletRequestAttributes attributes = getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException("respond attributes is null");
        }
        return attributes.getResponse();
    }

}
