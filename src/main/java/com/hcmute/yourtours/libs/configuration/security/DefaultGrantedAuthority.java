package com.hcmute.yourtours.libs.configuration.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class DefaultGrantedAuthority implements GrantedAuthority {
    private String role;

    public DefaultGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
