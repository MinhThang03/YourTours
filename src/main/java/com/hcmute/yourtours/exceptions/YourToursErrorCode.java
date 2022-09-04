package com.hcmute.yourtours.exceptions;

import com.hcmute.yourtours.libs.exceptions.IErrorCode;
import org.springframework.http.HttpStatus;

public enum YourToursErrorCode implements IErrorCode {

    //400
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "4000000", "Đăng nhập không thành công"),

    //403
    FORBIDDEN(HttpStatus.FORBIDDEN, "4030000", "Bạn không đủ quyền truy cập"),

    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    YourToursErrorCode(HttpStatus httpStatus, String code, String message) {
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
