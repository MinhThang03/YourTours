package com.hcmute.yourtours.libs.model.factory.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;

@Data
public class FactoryGetResponse<I, T extends BaseData<I>> {
    @JsonUnwrapped
    private T data;
}
