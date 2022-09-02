package com.hcmute.yourtours.libs.configuration.security;

import org.springframework.security.core.AuthenticationException;
import com.hcmute.yourtours.libs.exceptions.CustomException;
import com.hcmute.yourtours.libs.exceptions.IErrorCode;

public class DefaultAuthenticationException extends AuthenticationException implements CustomException {
    private final IErrorCode iErrorCode;
    private final String errorMessage;

    public DefaultAuthenticationException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.iErrorCode = errorCode;
        this.errorMessage = null;
    }

    public DefaultAuthenticationException(IErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.iErrorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public IErrorCode getErrorCode() {
        return this.iErrorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
