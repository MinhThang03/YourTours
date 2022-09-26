package com.hcmute.yourtours.utils;

import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetInfoToken {

    public static String getCurrentSessionId() {
        if (getInfoUser() == null) {
            return null;
        }
        return getInfoUser().getSessionState();
    }


    public static String getUsername() {
        if (getInfoUser() == null) {
            return null;
        }
        return getInfoUser().getPreferredUsername();
    }

    public static DefaultUserDetail getInfoUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultUserDetail) {
            return (DefaultUserDetail) authentication.getPrincipal();
        }
        return null;
    }
}
