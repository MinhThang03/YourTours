package com.hcmute.yourtours.models.homes.projections;

import java.util.UUID;

public interface CalculateAverageRateProjection {
    UUID getId();

    Long getNumberOfReview();

    Double getRates();

    Double getAverageRate();

}
