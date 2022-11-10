package com.hcmute.yourtours.models.user_evaluate.filter;

import com.hcmute.yourtours.enums.EvaluateFilterTypeEnum;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EvaluateFilter implements BaseFilter {
    private EvaluateFilterTypeEnum typeFilter;
    private UUID homeId;
}
