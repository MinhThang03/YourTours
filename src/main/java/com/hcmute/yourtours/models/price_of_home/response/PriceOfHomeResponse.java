package com.hcmute.yourtours.models.price_of_home.response;

import com.hcmute.yourtours.models.price_of_home.models.ArrayPriceAndDayModels;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PriceOfHomeResponse {
    private double totalCost;
    private List<ArrayPriceAndDayModels> detail;
}
