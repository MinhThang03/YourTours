package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.exceptions.CustomException;
import com.hcmute.yourtours.libs.exceptions.ErrorResponse;
import com.hcmute.yourtours.libs.exceptions.IErrorCode;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Factory create response entity
 */
public interface IResponseFactory {
    <I> ResponseEntity<BaseResponse<I>> success(I data);

    ResponseEntity<ErrorResponse> error(IErrorCode errorCode);

    ResponseEntity<ErrorResponse> error(IErrorCode errorCode, Map<String, String> fieldsError);

    ResponseEntity<ErrorResponse> error(CustomException exception);
}
