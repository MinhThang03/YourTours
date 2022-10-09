package com.hcmute.yourtours.models.amenities_of_home;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class AmenityOfHomeInfo extends BaseData<UUID> {
    private Boolean isHave;

    private UUID amenityId;

    private UUID homeId;
}
