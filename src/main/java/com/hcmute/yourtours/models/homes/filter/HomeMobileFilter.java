package com.hcmute.yourtours.models.homes.filter;

import lombok.Data;

import java.util.UUID;

@Data
public class HomeMobileFilter {
    private String province;
    private UUID amenityId;
}
