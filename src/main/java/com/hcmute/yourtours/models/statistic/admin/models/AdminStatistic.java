package com.hcmute.yourtours.models.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AdminStatistic implements Serializable {
    private Long totalNumberOfGuests;
    private Long totalNumberOfOwner;
    private Long totalNumberOfBooking;
    private Double totalNumberOfRevenue;
}
