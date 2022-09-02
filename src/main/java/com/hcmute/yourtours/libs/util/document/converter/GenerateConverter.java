package com.hcmute.yourtours.libs.util.document.converter;

public interface GenerateConverter<S, D> {
    D convert(S s);
}
