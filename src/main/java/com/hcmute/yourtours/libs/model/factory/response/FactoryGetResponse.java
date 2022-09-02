package com.hcmute.yourtours.libs.model.factory.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import com.hcmute.yourtours.libs.model.BaseData;

@Data
public class FactoryGetResponse<I, T extends BaseData<I>> {
    @JsonUnwrapped
    private T data;
}
