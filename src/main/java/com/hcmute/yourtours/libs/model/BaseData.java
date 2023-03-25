package com.hcmute.yourtours.libs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class BaseData<T extends Serializable> implements Serializable {

    @Valid
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected T id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseData<?> baseData = (BaseData<?>) o;
        return Objects.equals(id, baseData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
