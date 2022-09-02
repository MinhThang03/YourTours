package com.hcmute.yourtours.libs.configuration.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        try {
            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user instanceof DefaultUserDetail) {
                DefaultUserDetail userPrincipal = (DefaultUserDetail) user;
                MDC.put("userId", userPrincipal.getSubject());
                MDC.put("phone", userPrincipal.getPhoneNumber());
                MDC.put("fullName", userPrincipal.getName());
            }
        } catch (Exception ignored) {
        }
        return true;
    }
}
