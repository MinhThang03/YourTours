package com.hcmute.yourtours.models.amenities_of_home.projection;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AmenityOfHomeModel {
    private String name;

    private String description;

    private CommonStatusEnum status;

    private Boolean isHave;

    private UUID amenityId;

    private UUID homeId;
}
