package com.hcmute.yourtours.models.owner_of_home.projections;

import java.util.UUID;

public interface StatisticInfoOwnerProjection {
    Long getNumberOfHomes();

    UUID getUserId();

    String getFullName();

    Long getNumberOfBooking();

    Double getTotalCost();
}
