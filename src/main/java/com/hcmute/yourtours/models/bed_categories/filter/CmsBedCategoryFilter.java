package com.hcmute.yourtours.models.bed_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

@Data
public class CmsBedCategoryFilter implements BaseFilter {
    private String keyword;
}
