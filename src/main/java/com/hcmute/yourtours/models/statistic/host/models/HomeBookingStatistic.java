package com.hcmute.yourtours.models.statistic.host.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HomeBookingStatistic {
    private String homeName;
    private Long numberOfBooking;
    private UUID homeId;
}
