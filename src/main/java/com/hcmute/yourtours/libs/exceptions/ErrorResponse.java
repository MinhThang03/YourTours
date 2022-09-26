package com.hcmute.yourtours.libs.exceptions;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ErrorResponse extends BaseResponse<Object> {
    private String message;
    private String code;
}
