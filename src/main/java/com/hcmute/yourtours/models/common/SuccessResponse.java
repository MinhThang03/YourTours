package com.hcmute.yourtours.models.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SuccessResponse {
    private boolean success;
}
