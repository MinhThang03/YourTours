package com.hcmute.yourtours.models.statistic.host.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OwnerStatistic {
    Long totalNumberOfBooking;
    Long totalNumberOfBookingFinish;
    List<HomeBookingStatistic> homeStatistic;
    List<RevenueStatistic> revenueStatistics;
}
