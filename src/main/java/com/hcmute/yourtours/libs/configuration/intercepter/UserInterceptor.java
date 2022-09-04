package com.hcmute.yourtours.libs.configuration.intercepter;

import com.hcmute.yourtours.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

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
            if (user instanceof CustomUserDetails) {
                CustomUserDetails userPrincipal = (CustomUserDetails) user;
                MDC.put("userId", userPrincipal.getUser().getId().toString());
                MDC.put("userName", userPrincipal.getUsername());
            }
        } catch (Exception ignored) {
        }
        return true;
    }
}
