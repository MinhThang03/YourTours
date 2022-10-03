package com.hcmute.yourtours.controllers.auth;


import com.hcmute.yourtours.factories.auth.IAuthFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.requests.LoginRequest;
import com.hcmute.yourtours.models.response.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@Tag(name = "AUTH API: USER")
@Transactional
public class AuthController implements IAuthController {

    protected final IResponseFactory iResponseFactory;
    private final IAuthFactory iAuthFactory;

    public AuthController(IResponseFactory iResponseFactory, IAuthFactory iAuthFactory) {
        this.iResponseFactory = iResponseFactory;
        this.iAuthFactory = iAuthFactory;
    }


    @Override
    public ResponseEntity<BaseResponse<LoginResponse>> login(LoginRequest request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            LoginResponse response = iAuthFactory.login(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }


}
