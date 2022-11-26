package com.hcmute.yourtours.exceptions;

import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.ErrorResponse;
import com.hcmute.yourtours.libs.exceptions.GlobalExceptionHandler;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class YourToursExceptionHandler extends GlobalExceptionHandler {
    public YourToursExceptionHandler(IResponseFactory iResponseFactory) {
        super(iResponseFactory);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException e) {
        return iResponseFactory.error(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException e) {
        return iResponseFactory.error(ErrorCode.CONFLICT);
    }
}
