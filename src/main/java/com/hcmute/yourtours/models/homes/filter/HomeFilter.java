package com.hcmute.yourtours.models.homes.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.UUID;

@Data
public class HomeFilter implements BaseFilter {

    @JsonIgnore
    private UUID userId;
}
