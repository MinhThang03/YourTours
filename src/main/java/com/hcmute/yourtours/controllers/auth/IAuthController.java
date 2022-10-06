package com.hcmute.yourtours.controllers.auth;


import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.requests.LoginRequest;
import com.hcmute.yourtours.models.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.response.LoginResponse;
import com.hcmute.yourtours.models.response.LogoutResponse;
import com.hcmute.yourtours.models.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface IAuthController {
    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    @SecurityRequirements
    ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request);

    @DeleteMapping("/logout")
    @Operation(summary = "Đăng xuất")
    ResponseEntity<BaseResponse<LogoutResponse>> logout();

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token")
    ResponseEntity<BaseResponse<RefreshTokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request, HttpServletRequest context);


}
