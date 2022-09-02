package com.hcmute.yourtours.libs.model.factory.request;

import lombok.Data;
import lombok.experimental.Accessors;
import com.hcmute.yourtours.libs.model.BaseData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class FactoryUpdateRequest<I, T extends BaseData<I>> {
    @NotNull
    private I id;

    @NotNull
    @Valid
    private T data;
}
