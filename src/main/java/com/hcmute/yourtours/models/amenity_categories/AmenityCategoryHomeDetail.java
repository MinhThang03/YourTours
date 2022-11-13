package com.hcmute.yourtours.models.amenity_categories;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AmenityCategoryHomeDetail {
    @JsonUnwrapped
    private AmenityCategoryInfo amenityCategory;
    private List<AmenityOfHomeModel> childAmenities;
}
