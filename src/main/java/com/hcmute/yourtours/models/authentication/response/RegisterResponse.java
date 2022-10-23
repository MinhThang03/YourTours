package com.hcmute.yourtours.models.authentication.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RegisterResponse {
    private boolean success;
}
