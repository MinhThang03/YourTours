package com.hcmute.yourtours.enums;

public enum BookRoomStatusEnum {
    WAITING("Đang chờ"),
    CHECK_IN("Đã nhận phòng"),
    CHECK_OUT("Đã rời khỏi"),
    CANCELED("Đã hủy");

    private final String description;

    BookRoomStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
