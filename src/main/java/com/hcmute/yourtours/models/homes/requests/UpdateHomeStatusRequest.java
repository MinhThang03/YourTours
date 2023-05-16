package com.hcmute.yourtours.models.homes.requests;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateHomeStatusRequest {
    private UUID homeId;
    private CommonStatusEnum status;

}
