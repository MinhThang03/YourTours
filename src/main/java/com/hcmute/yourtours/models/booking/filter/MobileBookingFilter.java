package com.hcmute.yourtours.models.booking.filter;

import com.hcmute.yourtours.enums.MobileBookHomeStatusEnum;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MobileBookingFilter implements BaseFilter {
    @NotNull
    MobileBookHomeStatusEnum status;
}
