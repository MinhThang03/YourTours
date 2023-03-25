package com.hcmute.yourtours.libs.audit;

import com.hcmute.yourtours.libs.configuration.security.DefaultUserDetail;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DefaultUserDetail) {
            DefaultUserDetail defaultUserDetail = (DefaultUserDetail) authentication.getPrincipal();
            return Optional.ofNullable(defaultUserDetail.getSubject() != null ? defaultUserDetail.getSubject() : null);
        }
        String userId = null;
        try {
            userId = MDC.get("userId");
            if (userId != null && !userId.isBlank() && !userId.equals("System")) {
                return Optional.of(userId);
            }
        } catch (Exception e) {
            LogContext.push(LogType.TRACING, "Error when get Auditor: UserId " + userId + " from MDC " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        return Optional.empty();
    }


}
