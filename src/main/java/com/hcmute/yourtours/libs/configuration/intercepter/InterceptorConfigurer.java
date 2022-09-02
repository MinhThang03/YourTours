package com.hcmute.yourtours.libs.configuration.intercepter;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InitInterceptor());
        registry.addInterceptor(new UserInterceptor());
        registry.addInterceptor(new LogInterceptor());
    }
}
