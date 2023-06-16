package com.hcmute.yourtours.models.amenities.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.UUID;

@Data
public class AmenityFilter implements BaseFilter {
    private UUID categoryId;
    private String keyword;
}
