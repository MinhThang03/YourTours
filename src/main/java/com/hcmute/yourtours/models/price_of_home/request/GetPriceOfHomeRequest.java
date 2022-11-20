package com.hcmute.yourtours.models.price_of_home.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class GetPriceOfHomeRequest {

    @NotNull
    private UUID homeId;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;
}
