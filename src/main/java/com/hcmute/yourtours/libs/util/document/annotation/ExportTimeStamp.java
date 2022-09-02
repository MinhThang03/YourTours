package com.hcmute.yourtours.libs.util.document.annotation;

import com.hcmute.yourtours.libs.util.constant.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface ExportTimeStamp {
    String pattern() default Constants.DATE_PATTERN;
}
