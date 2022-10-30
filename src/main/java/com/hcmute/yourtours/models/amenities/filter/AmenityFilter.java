package com.hcmute.yourtours.models.amenities.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

@Data
public class AmenityFilter implements BaseFilter {
    private Boolean isDefault;
}
