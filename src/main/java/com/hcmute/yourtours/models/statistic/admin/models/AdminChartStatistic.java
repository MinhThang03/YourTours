package com.hcmute.yourtours.models.statistic.admin.models;

import com.hcmute.yourtours.models.statistic.common.RevenueStatistic;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdminChartStatistic {
    private List<RevenueStatistic> revenueStatistics;
}
