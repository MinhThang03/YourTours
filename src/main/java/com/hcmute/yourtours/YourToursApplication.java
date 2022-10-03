package com.hcmute.yourtours;

import com.hcmute.yourtours.libs.configuration.auto.EnableCommonAutoConfig;
import com.hcmute.yourtours.uuid_projection.envers.UUIDEnversRepositoryFactoryBean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "Authorization",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "Bearer [token]",
                        scheme = "bearer",
                        in = SecuritySchemeIn.HEADER,
                        description = "Service to Service Access token"
                ),
                @SecurityScheme(
                        name = "x-api-key",
                        type = SecuritySchemeType.APIKEY,
                        in = SecuritySchemeIn.HEADER
                )
        }
)
@EnableCaching
@OpenAPIDefinition(
        info = @Info(title = "Parking API", version = "1.0", description = "Documentation Parking Translate API v1.0"),
        security = @SecurityRequirement(name = "Authorization"))
@EnableCommonAutoConfig
@EnableEnversRepositories
@EnableScheduling
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(repositoryFactoryBeanClass = UUIDEnversRepositoryFactoryBean.class, basePackages = {"com.hcmute.yourtours.repositories"})
public class YourToursApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourToursApplication.class, args);
    }


}
