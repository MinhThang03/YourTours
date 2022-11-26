package com.hcmute.yourtours.libs.exceptions;

import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.ExceptionLog;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    protected final IResponseFactory iResponseFactory;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        Map<String, String> fieldsError = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            fieldsError.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error("{}", fieldsError);
        LogContext.push(LogType.RESPONSE, new ExceptionLog(e, false));
        return iResponseFactory.error(
                ErrorCode.CONFLICT,
                fieldsError
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentMisMatchException(MethodArgumentTypeMismatchException e) {
        log.error("{}", e.getMessage());
        LogContext.push(LogType.RESPONSE, new ExceptionLog(e, false));
        return iResponseFactory.error(ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorResponse> customRestExceptionHandler(RestException e) {
        log.error("{} - {}", e.getErrorCode().getCode(), e.getErrorMessage());
        LogContext.push(LogType.RESPONSE, new ExceptionLog(e, false));
        return iResponseFactory.error(e);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorResponse> customInvalidExceptionHandler(InvalidException e) {
        log.error("{} - {}", e.getErrorCode().getCode(), e.getErrorMessage());
        LogContext.push(LogType.RESPONSE, new ExceptionLog(e, false));
        return iResponseFactory.error(e);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedExceptionHandler(Exception e) {
        log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        return iResponseFactory.error(new RestException(YourToursErrorCode.FORBIDDEN));
    }

}
