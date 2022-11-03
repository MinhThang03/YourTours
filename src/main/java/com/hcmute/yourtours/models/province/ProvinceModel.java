package com.hcmute.yourtours.models.province;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvinceModel {
    private String provinceCode;
    private Long numberBooking;
}
