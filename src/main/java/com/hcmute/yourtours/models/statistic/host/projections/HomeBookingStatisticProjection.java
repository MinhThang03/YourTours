package com.hcmute.yourtours.models.statistic.host.projections;

import java.util.UUID;

public interface HomeBookingStatisticProjection {
    String getHomeName();

    UUID getHomeId();

    Long getNumberOfBooking();

}
