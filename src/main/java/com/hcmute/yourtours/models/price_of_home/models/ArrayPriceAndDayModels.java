package com.hcmute.yourtours.models.price_of_home.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ArrayPriceAndDayModels {
    private double cost;
    private LocalDate day;
}
