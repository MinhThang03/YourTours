package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IAppNotificationController;
import com.hcmute.yourtours.factories.notification.INotificationFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.notification.NotificationInfo;
import com.hcmute.yourtours.models.notification.request.TickViewRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/notification")
@Tag(name = "APP API: NOTIFICATION", description = "API xử lý notification của khách hàng")
@Transactional
public class AppNotificationController
        extends BaseController<UUID, NotificationInfo, NotificationInfo>
        implements IAppNotificationController {


    private final INotificationFactory iNotificationFactory;

    protected AppNotificationController(INotificationFactory iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        iNotificationFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> tickView(TickViewRequest request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iNotificationFactory.tickView(request.getNotificationId());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> deleteList(Boolean all) {
        try {
            LogContext.push(LogType.REQUEST, all);
            SuccessResponse response = iNotificationFactory.deleteListNotificationOfCurrentUser(all);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
