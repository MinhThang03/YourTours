package com.hcmute.yourtours.models.bed_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.UUID;

@Data
public class BedCategoryFilter implements BaseFilter {

    private UUID roomHomeId;
}
