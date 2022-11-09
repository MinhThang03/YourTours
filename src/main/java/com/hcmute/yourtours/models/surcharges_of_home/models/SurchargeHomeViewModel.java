package com.hcmute.yourtours.models.surcharges_of_home.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class SurchargeHomeViewModel implements Serializable {
    private UUID surchargeCategoryId;
    private String surchargeCategoryName;
    private Double cost;
    private String description;
}
