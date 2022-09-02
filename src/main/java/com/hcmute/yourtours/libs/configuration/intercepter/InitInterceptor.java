package com.hcmute.yourtours.libs.configuration.intercepter;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        try {
            MDC.clear();
            MDC.put("path", request.getMethod() + ":" + request.getRequestURI());
            MDC.put("sessionId", request.getSession().getId());
        } catch (Exception ignored) {
        }
        return true;
    }
}
