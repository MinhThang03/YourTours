package com.hcmute.yourtours.models.price_of_home.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PriceOfHomeCreateRequest {

    @NotNull
    private UUID homeId;

    @NotNull
    private LocalDate dateStart;

    @NotNull
    private LocalDate dateEnd;

    @NotNull
    @Min(value = 0, message = "Không được phép nhỏ hơn 0")
    private Double price;
}
