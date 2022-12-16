package com.hcmute.yourtours.models.statistic.host.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class HomeBookingStatistic implements Serializable {
    private String homeName;
    private Long numberOfBooking;
    private UUID homeId;
}
