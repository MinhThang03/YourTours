package com.hcmute.yourtours.libs.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public abstract class BaseData<T extends Serializable> implements Serializable {

    @Valid
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
