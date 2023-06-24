package com.hcmute.yourtours.models.statistic.admin.filter;

import com.hcmute.yourtours.enums.AdminChartTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminHomeChartFilter {
    private Integer year;

    @NotNull
    private AdminChartTypeEnum type;
}
