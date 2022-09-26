package com.hcmute.yourtours.libs.configuration.security;

import com.hcmute.yourtours.libs.configuration.properties.XApiKeyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@EnableConfigurationProperties(XApiKeyProperties.class)
public class XApiCheckFilter extends OncePerRequestFilter implements Filter {
    private final XApiKeyProperties properties;
    private final AntPathMatcher matcher = new AntPathMatcher();
    private final String X_API_KEY = "x-api-key";

    public XApiCheckFilter(XApiKeyProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (properties == null || properties.getServices() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        for (Map.Entry<String, XApiKeyProperties.ApiKeyPrefix> apiKeyPrefix : properties.getServices().entrySet()) {
            for (String prefix : apiKeyPrefix.getValue().getPrefixes()) {
                if (matcher.match(prefix, request.getRequestURI())) {
                    String key = request.getHeader(X_API_KEY);
                    if (Objects.nonNull(apiKeyPrefix.getValue()) && Objects.nonNull(apiKeyPrefix.getValue().getKeys())) {
                        for (XApiKeyProperties.KeyEnable apiKey : apiKeyPrefix.getValue().getKeys()) {
                            if (apiKey.getValue().equals(key) && apiKey.getEnable()) {
                                SecurityContext context = SecurityContextHolder.createEmptyContext();
                                Authentication authenticationResult = new XAPIAuthentication(apiKeyPrefix.getKey(), key);
                                context.setAuthentication(authenticationResult);
                                SecurityContextHolder.setContext(context);
                                filterChain.doFilter(request, response);
                                return;
                            }
                        }
                    }
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
