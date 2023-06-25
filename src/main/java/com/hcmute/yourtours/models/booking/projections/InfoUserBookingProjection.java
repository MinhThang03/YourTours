package com.hcmute.yourtours.models.booking.projections;

import java.util.UUID;

public interface InfoUserBookingProjection {
    UUID getUserId();

    String getFullName();

    String getEmail();

    Double getAverageRate();

    Double getTotalCost();

    Long getNumberOfBooking();
}
