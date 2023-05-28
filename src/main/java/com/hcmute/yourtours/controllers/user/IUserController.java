package com.hcmute.yourtours.controllers.user;

import com.hcmute.yourtours.libs.controller.IGetDetailByIdController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.request.SettingLanguageRequest;
import com.hcmute.yourtours.models.user.response.SettingLanguageResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface IUserController extends
        IGetDetailByIdController<UUID, UserDetail> {


    @GetMapping("/profile")
    @Operation(summary = "Lấy thông tin của user đang đăng nhập")
    ResponseEntity<BaseResponse<UserDetail>> getProfileCurrentUser();

    @PutMapping("update/current-user")
    @Operation(summary = "Cập nhật thông tin cá nhân của user đang đăng nhập")
    ResponseEntity<BaseResponse<UserDetail>> updateCurrentUser(@RequestBody @Valid UserDetail request);

    @PutMapping("setting/languages")
    @Operation(summary = "Cập nhật thông tin ngôn ngữ")
    ResponseEntity<BaseResponse<SettingLanguageResponse>> settingLanguage(@RequestBody @Valid SettingLanguageRequest request);

    @PostMapping("request/active")
    @Operation(summary = "Yêu cầu gửi otp kích hoạt tài khoản")
    ResponseEntity<BaseResponse<SuccessResponse>> requestActiveAccount();
}
