package com.hcmute.yourtours.libs.exceptions;

public class RestException extends RuntimeException implements CustomException {

    private final IErrorCode errorCode;
    private final String errorMessage;

    public RestException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

    public RestException(IErrorCode errorCode, String errorMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public IErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
