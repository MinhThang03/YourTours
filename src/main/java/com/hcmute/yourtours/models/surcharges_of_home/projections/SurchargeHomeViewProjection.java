package com.hcmute.yourtours.models.surcharges_of_home.projections;

import java.util.UUID;

public interface SurchargeHomeViewProjection {
    UUID getSurchargeCategoryId();

    String getSurchargeCategoryName();

    Double getCost();

    String getDescription();

}
