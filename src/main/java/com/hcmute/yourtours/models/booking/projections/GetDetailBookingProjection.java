package com.hcmute.yourtours.models.booking.projections;

import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface GetDetailBookingProjection {
    String getHomeName();

    Double getCostPerNight();

    String getProvince();

    Double getTotalCost();

    Double getMoneyPayed();

    LocalDate getDateStart();

    LocalDate getDateEnd();

    LocalDateTime getCreatedDate();

    String getComment();

    Double getRates();

    BookRoomStatusEnum getStatus();

    UUID getUserId();

    UUID getHomeId();

    String getUserName();

    UUID getBookingId();

    RefundPolicyEnum getRefundPolicy();

    String getOwnerName();
}
