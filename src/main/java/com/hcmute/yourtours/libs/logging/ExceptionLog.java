package com.hcmute.yourtours.libs.logging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExceptionLog {
    private final String exception;
    private final String message;
    private final String errorMessage;

    private final String throwAt;

    private final int line;
    private final List<String> stackTrace;

    public ExceptionLog(Exception e, boolean logStackTrace) {
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.errorMessage = null;
        this.throwAt = e.getStackTrace()[0].getClassName();
        this.line = e.getStackTrace()[0].getLineNumber();

        if (logStackTrace) {
            stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
        } else {
            stackTrace = null;
        }
    }

    public ExceptionLog(Exception e, String message, boolean logStackTrace) {
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.errorMessage = message;
        this.throwAt = e.getStackTrace()[0].getClassName();
        this.line = e.getStackTrace()[0].getLineNumber();

        if (logStackTrace) {
            stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
        } else {
            stackTrace = null;
        }
    }
}
