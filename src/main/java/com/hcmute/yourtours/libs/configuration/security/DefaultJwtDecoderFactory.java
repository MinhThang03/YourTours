package com.hcmute.yourtours.libs.configuration.security;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.util.CollectionUtils;
import com.hcmute.yourtours.libs.configuration.properties.SecurityConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class DefaultJwtDecoderFactory implements JwtDecoderFactory<SecurityConfigurationProperties.Jwt> {

    private void jwsAlgorithms(Set<SignatureAlgorithm> signatureAlgorithms, SecurityConfigurationProperties.Jwt context) {
        for (String algorithm : context.getJwsAlgorithms()) {
            signatureAlgorithms.add(SignatureAlgorithm.from(algorithm));
        }
    }

    private OAuth2TokenValidator<Jwt> getValidators(Supplier<OAuth2TokenValidator<Jwt>> defaultValidator, SecurityConfigurationProperties.Jwt context) {
        OAuth2TokenValidator<Jwt> defaultValidators = defaultValidator.get();
        List<String> audiences = context.getAudiences();
        if (CollectionUtils.isEmpty(audiences)) {
            return defaultValidators;
        }
        List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
        validators.add(defaultValidators);
        validators.add(new JwtClaimValidator<List<String>>(JwtClaimNames.AUD,
                (aud) -> aud != null && !Collections.disjoint(aud, audiences)));
        return new DelegatingOAuth2TokenValidator<>(validators);
    }

    @Override
    public JwtDecoder createDecoder(SecurityConfigurationProperties.Jwt context) {
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withJwkSetUri(context.getJwkSetUri())
                .jwsAlgorithms(
                        signatureAlgorithms -> {
                            jwsAlgorithms(signatureAlgorithms, context);
                        }
                ).build();
        String issuerUri = context.getIssuerUri();
        Supplier<OAuth2TokenValidator<Jwt>> defaultValidator = (issuerUri != null)
                ? () -> JwtValidators.createDefaultWithIssuer(issuerUri) : JwtValidators::createDefault;
        nimbusJwtDecoder.setJwtValidator(getValidators(defaultValidator, context));
        return nimbusJwtDecoder;
    }

}
