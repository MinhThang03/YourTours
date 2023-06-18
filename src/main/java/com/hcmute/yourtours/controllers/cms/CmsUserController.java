package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.IUserController;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import com.hcmute.yourtours.models.user.filter.CmsUserFilter;
import com.hcmute.yourtours.models.user.request.UpdateUserStatusRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/user")
@Tag(name = "CMS API: USER", description = "Quan ly nguoi dung")
@Transactional
public class CmsUserController
        extends BaseController<UUID, UserInfo, UserDetail>
        implements IUserController {

    private final IUserFactory iUserFactory;

    protected CmsUserController(IUserFactory iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        this.iUserFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<UserInfo>>> getInfoPageWithFilter(CmsUserFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<SuccessResponse>> updateStatus(UpdateUserStatusRequest request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iUserFactory.updateStatus(request.getId(), request.getStatus());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
