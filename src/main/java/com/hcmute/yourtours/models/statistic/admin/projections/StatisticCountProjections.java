package com.hcmute.yourtours.models.statistic.admin.projections;

public interface StatisticCountProjections {
    Long getNumberOfGuests();

    Long getNumberOfOwner();

    Long getNumberOfBooking();

    Double getTotalCost();
}
