package com.hcmute.yourtours.models.owner_of_home.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StatisticInfoOwnerModel {
    private Long numberOfHomes;

    private UUID userId;

    private String fullName;
    private String email;

    private Long numberOfBooking;

    private Double totalCost;
}
