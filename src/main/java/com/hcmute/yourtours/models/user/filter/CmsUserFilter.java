package com.hcmute.yourtours.models.user.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmsUserFilter implements BaseFilter {
    private String keyword;
}
