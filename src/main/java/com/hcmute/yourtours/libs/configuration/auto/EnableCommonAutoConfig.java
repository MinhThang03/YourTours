package com.hcmute.yourtours.libs.configuration.auto;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({CommonAutoConfiguration.class})
public @interface EnableCommonAutoConfig {
}
