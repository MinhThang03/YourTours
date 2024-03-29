package com.hcmute.yourtours.models.amenities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.common.NameDataModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class AmenityInfo extends NameDataModel<UUID> {

    private CommonStatusEnum status;

    private UUID categoryId;

    private String icon;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private AmenityCategoryDetail category;

    private Boolean setFilter;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isConfig;

}
