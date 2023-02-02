package com.hcmute.yourtours.libs.model.factory.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;

import java.io.Serializable;

@Data
public class FactoryGetResponse<I extends Serializable, T extends BaseData<I>> {
    @JsonUnwrapped
    private T data;
}
