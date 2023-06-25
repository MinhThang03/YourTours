package com.hcmute.yourtours.models.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AdminStatisticHome {
    private UUID homeId;

    private String homeName;

    private Long numberOfView;

    private Long numberOfBooking;

    private Double revenue;

    private Long numberOfEvaluate;

    private Double averageRate;

    private Double reservationRate;
    private String ownerName;
}
