package com.hcmute.yourtours.models.statistic.host.projections;

import java.util.UUID;


public interface OwnerHomeStatisticProjection {
    UUID getHomeId();

    String getHomeName();

    Long getNumberOfView();

    Long getNumberOfBooking();

    Double getRevenue();

    Long getNumberOfEvaluate();

    Double getPoint();

}
