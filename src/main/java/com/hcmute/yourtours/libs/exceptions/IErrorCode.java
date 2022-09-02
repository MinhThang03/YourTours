package com.hcmute.yourtours.libs.exceptions;

import org.springframework.http.HttpStatus;

public interface IErrorCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();

    String name();

}
