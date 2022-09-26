package com.hcmute.yourtours.libs.audit;

import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class DefaultAuditorAware implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultUserDetail) {
            DefaultUserDetail defaultUserDetail = (DefaultUserDetail) authentication.getPrincipal();
            return Optional.ofNullable(defaultUserDetail.getSubject() != null ? UUID.fromString(defaultUserDetail.getSubject()) : null);
        }
        return Optional.empty();
    }


}
