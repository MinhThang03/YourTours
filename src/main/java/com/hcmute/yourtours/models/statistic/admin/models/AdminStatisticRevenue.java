package com.hcmute.yourtours.models.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminStatisticRevenue {
    private String homeName;
    private String ownerName;
    private String customerName;
    private Double totalCost;
    private Double adminCost;
    private String createdDate;
}
