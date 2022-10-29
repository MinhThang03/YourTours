package com.hcmute.yourtours.models.amenities_of_home.projection;

import com.hcmute.yourtours.enums.CommonStatusEnum;

import java.util.UUID;

public interface AmenityOfHomeProjection {

    UUID getAmenityId();

    UUID getHomeId();

    String getName();

    String getDescription();

    CommonStatusEnum getStatus();

    Boolean getIsHave();
}
