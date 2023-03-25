package com.hcmute.yourtours.models.amenities_of_home;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class AmenityOfHomeInfo extends BaseData<UUID> {
    private Boolean isHave;

    @NotNull
    private UUID amenityId;

    private UUID homeId;
}
