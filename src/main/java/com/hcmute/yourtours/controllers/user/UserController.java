package com.hcmute.yourtours.controllers.user;

import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/")
@Tag(name = "API: USER")
@Transactional
public class UserController
        extends BaseController<UUID, UserInfo, UserDetail>
        implements IUserController {

    protected final IUserFactory iUserFactory;

    protected UserController(IUserFactory iDataFactory,
                             IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        this.iUserFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, UserDetail>>> getDetailById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponse<UserDetail>> getProfileCurrentUser() {
        try {
            UserDetail response = iUserFactory.getDetailCurrentUser();
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserDetail>> updateCurrentUser(UserDetail request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            UserDetail response = iUserFactory.updateCurrentUser(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> requestActiveAccount() {
        try {
            SuccessResponse response = iUserFactory.requestActiveAccount();
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }
}
