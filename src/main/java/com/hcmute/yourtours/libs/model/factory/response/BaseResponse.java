package com.hcmute.yourtours.libs.model.factory.response;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private boolean success;
    private T data;
}
