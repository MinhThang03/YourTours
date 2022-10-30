package com.hcmute.yourtours.models.price_of_home.response;

import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PriceOfHomeWithMonthResponse {
    private UUID homeId;
    private Integer month;
    private List<PriceOfHomeDetail> prices;
}
