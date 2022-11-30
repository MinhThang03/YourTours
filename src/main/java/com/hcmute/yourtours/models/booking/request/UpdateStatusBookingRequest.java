package com.hcmute.yourtours.models.booking.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UpdateStatusBookingRequest {
    @NotNull
    private UUID bookingId;
}
