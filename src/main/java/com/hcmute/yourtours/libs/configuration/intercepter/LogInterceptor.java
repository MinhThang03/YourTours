package com.hcmute.yourtours.libs.configuration.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import com.hcmute.yourtours.libs.logging.LogContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws Exception {
        try {
            String sessionId = MDC.get("sessionId");
            String logData = LogContext.pop(sessionId);
            if (logData != null) {
                log.info("Debugging for session {}\n{}", sessionId, logData);
            }
        } catch (Exception e) {
            log.warn("LogInterceptor.afterCompletion logging error in {}: {}", e.getClass().getSimpleName(), e.getMessage());
        }
        MDC.clear();
    }
}
