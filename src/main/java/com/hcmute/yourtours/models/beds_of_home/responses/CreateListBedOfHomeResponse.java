package com.hcmute.yourtours.models.beds_of_home.responses;

import com.hcmute.yourtours.models.common.SuccessResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreateListBedOfHomeResponse extends SuccessResponse {
    private String bedDescription;
}
