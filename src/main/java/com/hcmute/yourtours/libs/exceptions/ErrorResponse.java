package com.hcmute.yourtours.libs.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ErrorResponse extends BaseResponse<Object> {
    private String message;
    private String code;
}
