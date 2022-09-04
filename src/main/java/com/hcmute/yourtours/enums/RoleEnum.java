package com.hcmute.yourtours.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    USER("user"),

    ADMIN("admin"),

    COMPANY("company");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

}
