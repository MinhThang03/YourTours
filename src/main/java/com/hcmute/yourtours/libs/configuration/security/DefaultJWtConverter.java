package com.hcmute.yourtours.libs.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import com.hcmute.yourtours.libs.util.Mapper;

@Slf4j
public class DefaultJWtConverter implements Converter<Jwt, DefaultAuthentication> {
    @Override
    public DefaultAuthentication convert(Jwt source) {
        DefaultUserDetail userDetail = Mapper.convertValue(source.getClaims(), DefaultUserDetail.class);
        userDetail.setOtherClaims(source.getClaims());
        return new DefaultAuthentication(userDetail, source.getTokenValue(), userDetail.getAuthorities());
    }
}
