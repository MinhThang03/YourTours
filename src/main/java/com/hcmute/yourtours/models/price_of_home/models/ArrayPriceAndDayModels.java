package com.hcmute.yourtours.models.price_of_home.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArrayPriceAndDayModels {
    private double cost;
    private int day;
}
