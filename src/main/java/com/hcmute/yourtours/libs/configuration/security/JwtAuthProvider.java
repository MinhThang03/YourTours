package com.hcmute.yourtours.libs.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.util.Assert;

@Slf4j
public class JwtAuthProvider implements AuthenticationProvider {

    private final Log logger = LogFactory.getLog(getClass());

    private final JwtDecoder jwtDecoder;

    private final Converter<Jwt, DefaultAuthentication> jwtAuthenticationConverter = new DefaultJWtConverter();

    public JwtAuthProvider(JwtDecoder jwtDecoder) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DefaultAuthentication bearer = (DefaultAuthentication) authentication;
        Jwt jwt = getJwt(bearer);
        DefaultAuthentication token = this.jwtAuthenticationConverter.convert(jwt);
        token.setDetails(bearer.getDetails());
        this.logger.debug("Authenticated token");
        return token;
    }

    private Jwt getJwt(DefaultAuthentication bearer) {
        try {
            return this.jwtDecoder.decode(bearer.getToken());
        } catch (BadJwtException failed) {
            this.logger.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DefaultAuthentication.class.isAssignableFrom(authentication);
    }
}
