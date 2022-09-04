package com.hcmute.yourtours.libs.model.factory.request;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.experimental.Accessors;

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
