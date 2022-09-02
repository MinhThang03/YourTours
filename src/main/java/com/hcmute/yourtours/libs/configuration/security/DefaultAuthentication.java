package com.hcmute.yourtours.libs.configuration.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
public class DefaultAuthentication extends AbstractAuthenticationToken implements Authentication {
    private Object principal;
    private String token;

    public DefaultAuthentication(UserDetails principal, String token) {
        this(token);
        this.principal = principal;
    }

    public DefaultAuthentication(String token) {
        super(Collections.emptyList());
        Assert.hasText(token, "token cannot be empty");
        this.token = token;
    }

    public DefaultAuthentication(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        Assert.hasText(token, "token cannot be empty");
        this.token = token;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return this.getToken();
    }


    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
