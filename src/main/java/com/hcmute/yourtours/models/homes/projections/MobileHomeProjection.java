package com.hcmute.yourtours.models.homes.projections;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MobileHomeProjection {
    UUID getId();

    Double getCostPerNightDefault();

    String getName();

    Long getView();

    Long getNumberOfBooking();

    Long getFavorite();

    LocalDateTime getCreatedDate();

    Long getNumberOfReview();

    Double getRates();

    String getProvince();

    Double getAverageRate();

    boolean getIsFavorite();

    String getThumbnail();
}
