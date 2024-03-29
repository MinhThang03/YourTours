package com.hcmute.yourtours;

import com.hcmute.yourtours.libs.configuration.auto.EnableCommonAutoConfig;
import com.hcmute.yourtours.uuid_projection.envers.UUIDEnversRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@EnableJpaRepositories(repositoryFactoryBeanClass = UUIDEnversRepositoryFactoryBean.class, basePackages = {"com.hcmute.yourtours.repositories"})
public class YourToursApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourToursApplication.class, args);
    }

}
