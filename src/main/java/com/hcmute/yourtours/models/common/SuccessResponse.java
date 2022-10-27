package com.hcmute.yourtours.models.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    private boolean success;
}
