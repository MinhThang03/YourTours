package com.hcmute.yourtours.constant;

import lombok.Getter;

@Getter
public enum RoleConstant {
    USER("user"),
    ADMIN("admin");

    private final String role;

    RoleConstant(String role) {
        this.role = role;
    }
}
