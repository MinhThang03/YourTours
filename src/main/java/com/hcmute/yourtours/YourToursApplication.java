package com.hcmute.yourtours;

import com.hcmute.yourtours.libs.configuration.auto.EnableCommonAutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.EntityListeners;

@SpringBootApplication
@EnableCaching
@EnableCommonAutoConfig
@EnableScheduling
@EnableWebSecurity
@EntityListeners(AuditingEntityListener.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing
public class YourToursApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourToursApplication.class, args);
    }

}
