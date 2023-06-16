package com.hcmute.yourtours.enums;


import lombok.Getter;

@Getter
public enum RefundPolicyEnum {
    BEFORE_ONE_DAY("Hủy phòng trước 1 ngày"),
    NO_REFUND("Không cho phép hủy đặt"),
    BEFORE_SEVEN_DAYS("Hủy phòng trước 7 ngày");

    private final String description;

    RefundPolicyEnum(String description) {
        this.description = description;
    }
}
