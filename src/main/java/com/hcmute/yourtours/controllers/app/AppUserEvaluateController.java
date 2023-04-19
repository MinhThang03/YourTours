package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IAppUserEvaluateController;
import com.hcmute.yourtours.factories.user_evaluate.IUserEvaluateFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.models.user_evaluate.filter.EvaluateFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/user-evaluate")
@Tag(name = "APP API: USER EVALUATE", description = "API xử lý đánh giá của khách hàng")
@Transactional
public class AppUserEvaluateController
        extends BaseController<UUID, UserEvaluateInfo, UserEvaluateDetail>
        implements IAppUserEvaluateController {

    protected AppUserEvaluateController(
            @Qualifier("appUserEvaluateFactory") IUserEvaluateFactory iDataFactory,
            IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<UserEvaluateDetail>> createModel(FactoryCreateRequest<UUID, UserEvaluateDetail> request) {
        return factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<UserEvaluateInfo>>> getInfoPageWithFilter(EvaluateFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<UserEvaluateDetail>> updateModel(FactoryUpdateRequest<UUID, UserEvaluateDetail> request) {
        return factoryUpdateModel(request);
    }
}
