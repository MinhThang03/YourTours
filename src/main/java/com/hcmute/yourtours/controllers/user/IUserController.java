package com.hcmute.yourtours.controllers.user;

import com.hcmute.yourtours.libs.controller.IGetDetailByIdController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

public interface IUserController extends
        IGetDetailByIdController<UUID, UserDetail> {


    @GetMapping("/profile")
    @Operation(summary = "Lấy thông tin của user đang đăng nhập")
    ResponseEntity<BaseResponse<UserDetail>> getProfileCurrentUser();
}
