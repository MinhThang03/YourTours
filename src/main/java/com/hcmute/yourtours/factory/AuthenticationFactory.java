package com.hcmute.yourtours.factory;

import com.hcmute.yourtours.commands.UserCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.requests.LoginRequest;
import com.hcmute.yourtours.models.response.LoginResponse;
import com.hcmute.yourtours.repositories.UserRepository;
import com.hcmute.yourtours.security.CustomUserDetails;
import com.hcmute.yourtours.security.jwt.JwtTokenProvider;
import com.hcmute.yourtours.security.properties.SecurityConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationFactory implements IAuthenticationFactory {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final SecurityConfigurationProperties securityConfigurationProperties;
    @Autowired
    UserRepository userRepository;

    public AuthenticationFactory(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            SecurityConfigurationProperties securityConfigurationProperties) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.securityConfigurationProperties = securityConfigurationProperties;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws InvalidException {

        try {

            Optional<UserCommand> command = userRepository.findByUsername("admin");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            String refreshToken = tokenProvider.generateRefreshJwtToken((CustomUserDetails) authentication.getPrincipal());

            SecurityConfigurationProperties.Jwt jwtConfig = this.securityConfigurationProperties.getJwt();

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .expiresIn(jwtConfig.getJwtExpirationMs())
                    .refreshExpiresIn(jwtConfig.getRefreshJwtExpirationMs())
                    .refreshToken(refreshToken)
                    .tokenType(jwtConfig.getTokenPrefix())
                    .build();

        } catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.LOGIN_FAIL);
        }

    }
}
