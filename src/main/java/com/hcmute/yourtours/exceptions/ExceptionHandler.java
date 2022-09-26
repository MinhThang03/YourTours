package com.hcmute.yourtours.exceptions;

import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.ErrorResponse;
import com.hcmute.yourtours.libs.exceptions.GlobalExceptionHandler;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandler extends GlobalExceptionHandler {
    public ExceptionHandler(IResponseFactory iResponseFactory) {
        super(iResponseFactory);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentMisMatchException(MethodArgumentTypeMismatchException e) {
        return iResponseFactory.error(ErrorCode.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException e) {
        return iResponseFactory.error(ErrorCode.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException e) {
        return iResponseFactory.error(ErrorCode.CONFLICT);
    }
}
