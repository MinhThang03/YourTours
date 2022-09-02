package com.hcmute.yourtours.libs.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.hcmute.yourtours.libs.exceptions.CustomException;
import com.hcmute.yourtours.libs.exceptions.ErrorResponse;
import com.hcmute.yourtours.libs.exceptions.IErrorCode;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;

import java.util.Map;

public class DefaultResponseFactory implements IResponseFactory {

    @Override
    public <I> ResponseEntity<BaseResponse<I>> success(I data) {
        BaseResponse<I> response = new BaseResponse<>();
        response.setData(data);
        response.setSuccess(true);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @Override
    public ResponseEntity<ErrorResponse> error(IErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(getMessage(errorCode));
        response.setSuccess(false);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @Override
    public ResponseEntity<ErrorResponse> error(IErrorCode errorCode, Map<String, String> fieldsError) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(getMessage(errorCode));
        response.setData(fieldsError);
        response.setSuccess(false);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @Override
    public ResponseEntity<ErrorResponse> error(CustomException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(exception.getErrorCode().getCode());
        response.setSuccess(false);
        response.setMessage(getMessage(exception));
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    protected String getMessage(CustomException exception) {
        return exception.getErrorMessage() != null ? exception.getErrorMessage() : getMessage(exception.getErrorCode());
    }

    protected String getMessage(IErrorCode iErrorCode) {
        return iErrorCode.getMessage();
    }
}
