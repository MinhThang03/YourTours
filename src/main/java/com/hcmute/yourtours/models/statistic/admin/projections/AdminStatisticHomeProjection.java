package com.hcmute.yourtours.models.statistic.admin.projections;

import java.util.UUID;

public interface AdminStatisticHomeProjection {
    UUID getHomeId();

    String getHomeName();

    String getOwnerName();

    Long getNumberOfView();

    Long getNumberOfBooking();

    Double getRevenue();

    Long getNumberOfEvaluate();

    Double getPoint();
}
