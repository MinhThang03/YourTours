package com.hcmute.yourtours.libs.model.factory.request;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FactoryUpdateRequest<I extends Serializable, T extends BaseData<I>> {
    @NotNull
    private I id;

    @NotNull
    @Valid
    private T data;
}
