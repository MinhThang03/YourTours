package com.hcmute.yourtours.models.amenity_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

@Data
public class AmenityCategoryFilter implements BaseFilter {
    Boolean isDefault;
}
