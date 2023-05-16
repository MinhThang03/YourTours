package com.hcmute.yourtours.models.homes.responses;


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
public class UpdateHomeStatusResponse {
    private UUID homeId;
    private CommonStatusEnum status;
}
