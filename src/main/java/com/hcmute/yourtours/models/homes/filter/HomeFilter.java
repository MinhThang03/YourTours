package com.hcmute.yourtours.models.homes.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.yourtours.enums.HomeSortTypeEnum;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HomeFilter implements BaseFilter {

    @JsonIgnore
    private UUID userId;

    private HomeSortTypeEnum sort;
}
