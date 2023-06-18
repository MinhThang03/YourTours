package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.models.user.filter.CmsUserFilter;
import com.hcmute.yourtours.models.user.request.UpdateUserStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface IUserController extends IGetInfoPageController<UUID, UserInfo, CmsUserFilter> {

    @PutMapping("update/status")
    ResponseEntity<BaseResponse<SuccessResponse>> updateStatus(@RequestBody @Valid UpdateUserStatusRequest request);
}
