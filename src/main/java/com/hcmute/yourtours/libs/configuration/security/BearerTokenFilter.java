package com.hcmute.yourtours.libs.configuration.security;

import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BearerTokenFilter extends OncePerRequestFilter {
    private final AuthenticationEntryPoint authenticationEntryPoint = new DefaultAuthenticationEntryPoint();
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private final AuthenticationProvider provider;
    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
    private final RequestMatcher pathMatcher;


    public BearerTokenFilter(AuthenticationProvider provider) {
        this(provider, "/**");
    }


    public BearerTokenFilter(AuthenticationProvider provider, String pathPattern) {
        this.provider = provider;
        this.pathMatcher = new AntPathRequestMatcher(
                pathPattern
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!getPathMatcher().matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token;
        try {
            token = this.bearerTokenResolver.resolve(request);
        } catch (OAuth2AuthenticationException invalid) {
            this.logger.trace("Sending to authentication entry point since failed to resolve bearer token", invalid);
            authenticationEntryPoint.commence(request, response, new DefaultAuthenticationException(ErrorCode.UNAUTHORIZED));
            return;
        }
        if (token == null) {
            this.logger.trace("Did not process request since did not find bearer token");
            filterChain.doFilter(request, response);
            return;
        }
        DefaultAuthentication authenticationRequest = new DefaultAuthentication(token);
        authenticationRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        try {
            Authentication authenticationResult = provider.authenticate(authenticationRequest);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResult);
            SecurityContextHolder.setContext(context);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    protected RequestMatcher getPathMatcher() {
        return pathMatcher;
    }
}
