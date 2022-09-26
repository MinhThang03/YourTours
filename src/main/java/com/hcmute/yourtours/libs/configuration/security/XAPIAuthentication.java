package com.hcmute.yourtours.libs.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class XAPIAuthentication implements Authentication {

    private final String name;
    private final String xApiKey;

    public XAPIAuthentication(String name, String xApiKey) {
        this.name = name;
        this.xApiKey = xApiKey;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return xApiKey;
    }

    @Override
    public Object getDetails() {
        return xApiKey;
    }

    @Override
    public Object getPrincipal() {
        return xApiKey;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return name;
    }
}
