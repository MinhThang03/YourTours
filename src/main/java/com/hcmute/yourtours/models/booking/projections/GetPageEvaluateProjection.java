package com.hcmute.yourtours.models.booking.projections;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GetPageEvaluateProjection {
    String getAvatar();

    String getFullName();

    double getRates();

    String getComment();

    UUID getUserId();

    UUID getHomeId();

    UUID getBookingId();

    LocalDateTime getLastModifiedDate();
}
