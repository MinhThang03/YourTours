package com.hcmute.yourtours.models.booking.projections;

import com.hcmute.yourtours.enums.BookRoomStatusEnum;

import java.time.LocalDate;
import java.util.UUID;

public interface MobileGetPageBookingProjection {
    UUID getId();

    Double getTotalCost();

    LocalDate getDateStart();

    LocalDate getDateEnd();

    String getHomeName();

    String getThumbnail();

    String getProvinceName();

    BookRoomStatusEnum getStatus();
}
