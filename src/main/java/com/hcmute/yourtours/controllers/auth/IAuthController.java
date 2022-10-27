package com.hcmute.yourtours.controllers.auth;


import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.authentication.requests.LoginRequest;
import com.hcmute.yourtours.models.authentication.requests.RefreshTokenRequest;
import com.hcmute.yourtours.models.authentication.requests.RegisterRequest;
import com.hcmute.yourtours.models.authentication.requests.VerifyOtpRequest;
import com.hcmute.yourtours.models.authentication.response.*;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.request.ForgotPasswordRequest;
import com.hcmute.yourtours.models.user.request.ResetPasswordWithOtpRequest;
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

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản")
    ResponseEntity<BaseResponse<RegisterResponse>> registerAccount(@Valid @RequestBody RegisterRequest request);

    @PostMapping("/active-account")
    @Operation(summary = "Kích hoạt tài khoản")
    ResponseEntity<BaseResponse<VerifyOtpResponse>> activeAccount(@Valid @RequestBody VerifyOtpRequest request);

    @PostMapping("/forgot-password")
    @Operation(summary = "Gửi yêu cầu quên mật khẩu")
    ResponseEntity<BaseResponse<SuccessResponse>> forgotPasswordRequest(@Valid @RequestBody ForgotPasswordRequest request);

    @PostMapping("/otp/reset-password")
    @Operation(summary = "Đổi mật khẩu với otp")
    ResponseEntity<BaseResponse<SuccessResponse>> resetPasswordWithOtp(@Valid @RequestBody ResetPasswordWithOtpRequest request);

}
