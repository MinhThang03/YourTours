package com.hcmute.yourtours.libs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DataIdWrapper<ID, T extends BaseData<ID>> extends BaseData<ID> {
    @Valid
    private T data;

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    public ID getId() {
        return data.getId();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    public BaseData<ID> setId(ID id) {
        this.data.setId(id);
        super.setId(id);
        return this;
    }
}
