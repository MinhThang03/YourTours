package com.hcmute.yourtours.models.amenity_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.UUID;

@Data
public class AmenityCategoryFilter implements BaseFilter {
    Boolean isDefault;
    UUID homeId;
}
