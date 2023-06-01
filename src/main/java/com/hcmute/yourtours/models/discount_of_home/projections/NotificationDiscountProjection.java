package com.hcmute.yourtours.models.discount_of_home.projections;

import java.util.UUID;

public interface NotificationDiscountProjection {
    UUID getHomeId();

    String getHomeName();

    String getThumbnail();

    UUID getUserId();

    String getDiscountName();

    double getPercent();

}
