package com.hcmute.yourtours.models.booking.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class MonthAndYearModel {
    @Min(value = 1)
    @Max(value = 12)
    Integer month;

    Integer year;
}
