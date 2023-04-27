package com.hcmute.yourtours.models.booking.filter;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class BookingEvaluateFilter {

    @NotNull
    UUID homeId;
}
