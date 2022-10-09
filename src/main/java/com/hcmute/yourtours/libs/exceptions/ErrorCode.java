package com.hcmute.yourtours.libs.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements IErrorCode {

    OK(HttpStatus.OK, "2000000", "OK"),

    //500
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000000", "Server Error"),
    TOKEN_PROVIDER_NOTFOUND(HttpStatus.INTERNAL_SERVER_ERROR, "50000001", "Not found token provider"),


    //401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4010000", "Unauthorized"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "4010001", "Invalid Token"),
    INVALID_X_API_KEY(HttpStatus.UNAUTHORIZED, "4010002", "Invalid X-API-KEY"),


    //404
    NOT_FOUND(HttpStatus.NOT_FOUND, "4040000", "Not Found"),


    //409
    CONFLICT(HttpStatus.CONFLICT, "4090000", "Conflict"),
    INVALID_DATE_TYPE(HttpStatus.CONFLICT, "40900001", "Invalid date"),
    CONVERT_TO_STRING_ERROR(HttpStatus.CONFLICT, "4090002", "Convert error"),


    //400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "40000000", "Bad request"),

    INTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "40000001", "Bad Request");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
