package com.hcmute.yourtours.models.homes.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CmsHomeFilter implements BaseFilter {

    @JsonIgnore
    @Hidden
    private UUID userId;

    private List<CommonStatusEnum> statusList;

    private String keyword;

}
