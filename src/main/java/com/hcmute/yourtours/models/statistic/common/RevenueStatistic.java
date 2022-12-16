package com.hcmute.yourtours.models.statistic.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RevenueStatistic implements Serializable {
    private String month;
    private Double amount;
}
