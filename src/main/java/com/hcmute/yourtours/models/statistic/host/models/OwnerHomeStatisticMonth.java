package com.hcmute.yourtours.models.statistic.host.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerHomeStatisticMonth {

    private UUID homeId;

    private String homeName;

    private Long numberOfView;

    private Long numberOfBooking;

    private Double revenue;

    private Long numberOfEvaluate;

    private Double averageRate;

    private Double reservationRate;
}
