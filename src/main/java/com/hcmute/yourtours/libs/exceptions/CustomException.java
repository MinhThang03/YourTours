package com.hcmute.yourtours.libs.exceptions;

public interface CustomException {

    IErrorCode getErrorCode();

    String getErrorMessage();
}
