package com.hcmute.yourtours.libs.model.factory.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.experimental.Accessors;
import com.hcmute.yourtours.libs.model.BaseData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class FactoryCreateRequest<I, T extends BaseData<I>> {
    @NotNull
    @Valid
    @JsonUnwrapped
    private T data;
}
