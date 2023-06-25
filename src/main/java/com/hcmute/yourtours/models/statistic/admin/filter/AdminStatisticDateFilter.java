package com.hcmute.yourtours.models.statistic.admin.filter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AdminStatisticDateFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
}
