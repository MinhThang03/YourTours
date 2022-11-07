package com.hcmute.yourtours.factories.common;

import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;
import com.hcmute.yourtours.libs.configuration.security.XAPIAuthentication;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserFromTokenFactory implements IGetUserFromTokenFactory {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultUserDetail) {
            DefaultUserDetail defaultUserDetail = (DefaultUserDetail) authentication.getPrincipal();
            return Optional.ofNullable(defaultUserDetail.getSubject() != null ? defaultUserDetail.getSubject() : null);
        }
        if (authentication instanceof XAPIAuthentication) {
            return Optional.ofNullable(authentication.getName());
        }
        return Optional.empty();
    }

    @Override
    public UUID checkUnAuthorization() throws InvalidException {
        Optional<String> userId = getCurrentAuditor();
        if (userId.isEmpty()) {
            throw new InvalidException(ErrorCode.UNAUTHORIZED);
        }
        return UUID.fromString(userId.get());
    }
}
