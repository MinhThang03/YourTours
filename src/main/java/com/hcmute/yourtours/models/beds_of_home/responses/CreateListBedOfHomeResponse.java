package com.hcmute.yourtours.models.beds_of_home.responses;

import com.hcmute.yourtours.models.common.SuccessResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CreateListBedOfHomeResponse extends SuccessResponse {
    private String bedDescription;
}
