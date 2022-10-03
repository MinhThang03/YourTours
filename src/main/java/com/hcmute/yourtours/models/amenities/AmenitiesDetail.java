package com.hcmute.yourtours.models.amenities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoriesDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class AmenitiesDetail extends AmenitiesInfo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AmenityCategoriesDetail category;
}
