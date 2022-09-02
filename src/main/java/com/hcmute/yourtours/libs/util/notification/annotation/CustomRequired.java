package com.hcmute.yourtours.libs.util.notification.annotation;

import com.hcmute.yourtours.libs.util.notification.models.Channel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRequired {
    Channel[] justFor() default {};
}
