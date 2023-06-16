package com.hcmute.yourtours.models.discount_home_categories.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountHomeCategoryFilter implements BaseFilter {
    private String keyword;
}
