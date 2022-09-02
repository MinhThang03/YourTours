package com.hcmute.yourtours.libs.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class JwtUtil {
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    private JwtUtil() {
    }

    public static String getBearerToken() {
        return BEARER + getRawToken();
    }

    public static String getRawToken() {
        HttpServletRequest currentRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return getRawTokenFromRequest(currentRequest);
    }

    public static String getRawTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER) ? bearerToken.replaceFirst(BEARER, "") : null;
    }

    public static String getBearerTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER) ? bearerToken : null;
    }
}
