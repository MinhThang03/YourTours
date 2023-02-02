package com.hcmute.yourtours.libs.model.factory.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FactoryCreateRequest<I extends Serializable, T extends BaseData<I>> {
    @NotNull
    @Valid
    @JsonUnwrapped
    private T data;
}
