package com.hcmute.yourtours.models.statistic.host.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevenueStatistic {
    private String month;
    private Double amount;
}
