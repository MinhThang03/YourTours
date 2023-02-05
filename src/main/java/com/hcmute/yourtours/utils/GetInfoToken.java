package com.hcmute.yourtours.utils;

import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetInfoToken {

    private GetInfoToken(){}

    public static String getCurrentSessionId() {
        DefaultUserDetail user = getInfoUser();
        if (user == null) {
            return null;
        }
        return user.getSessionState();
    }


    public static String getUsername() {
        DefaultUserDetail user = getInfoUser();
        if (user == null) {
            return null;
        }
        return user.getPreferredUsername();
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
