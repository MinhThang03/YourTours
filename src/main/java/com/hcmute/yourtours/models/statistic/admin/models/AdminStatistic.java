package com.hcmute.yourtours.models.statistic.admin.models;

import com.hcmute.yourtours.models.statistic.common.RevenueStatistic;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class AdminStatistic implements Serializable {
    private Long totalNumberOfGuests;
    private Long totalNumberOfOwner;
    private Long totalNumberOfBooking;
    private Double totalNumberOfRevenue;
    private transient List<RevenueStatistic> revenueStatistics;
}
