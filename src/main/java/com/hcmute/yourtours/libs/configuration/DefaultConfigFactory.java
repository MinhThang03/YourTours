package com.hcmute.yourtours.libs.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import com.hcmute.yourtours.libs.configuration.properties.CommonConfigurationProperties;

public class DefaultConfigFactory extends BaseConfigFactory {
    private final String DEFAULT_AUTH_SERVICE = "auth";

    public DefaultConfigFactory(CommonConfigurationProperties properties) {
        super(properties);
    }

    @Override
    protected void configureFilter(HttpSecurity http) {
        if (isAuth(DEFAULT_AUTH_SERVICE)) {
            http.addFilterAfter(jwtTokenFilter(DEFAULT_AUTH_SERVICE), LogoutFilter.class);
        }
    }
}
