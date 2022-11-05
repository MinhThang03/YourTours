package com.hcmute.yourtours.config;

import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;
import com.hcmute.yourtours.libs.configuration.security.XAPIAuthentication;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultUserDetail) {
            DefaultUserDetail defaultUserDetail = (DefaultUserDetail) authentication.getPrincipal();
            return Optional.ofNullable(defaultUserDetail.getSubject() != null ? defaultUserDetail.getSubject() : null);
        }
        if (authentication instanceof XAPIAuthentication) {
//            XAPIAuthentication auth = (XAPIAuthentication) authentication;
            return Optional.ofNullable(authentication.getName());
        }
        return Optional.empty();
    }

    public UUID checkUnAuthorization() throws InvalidException {
        Optional<String> userId = getCurrentAuditor();
        if (userId.isEmpty()) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        }
        return UUID.fromString(userId.get());
    }
}
