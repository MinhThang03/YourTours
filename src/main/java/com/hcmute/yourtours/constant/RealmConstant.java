package com.hcmute.yourtours.constant;

import lombok.Getter;

@Getter
public enum RealmConstant {
    YOURTOUR("your-tour");

    private final String realmName;

    RealmConstant(String realmName) {
        this.realmName = realmName;
    }
}
