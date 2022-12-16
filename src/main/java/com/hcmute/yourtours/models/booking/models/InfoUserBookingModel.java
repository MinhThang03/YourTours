package com.hcmute.yourtours.models.booking.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class InfoUserBookingModel {
    private UUID userId;
    private String fullName;
    private Double totalCost;
    private Long numberOfBooking;
}
