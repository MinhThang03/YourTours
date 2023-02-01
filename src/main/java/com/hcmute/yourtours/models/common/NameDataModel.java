package com.hcmute.yourtours.models.common;

import com.hcmute.yourtours.libs.model.BaseData;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class NameDataModel<T> extends BaseData<T> {

    @NotNull
    @NotBlank
    private String name;

    private String description;
}
