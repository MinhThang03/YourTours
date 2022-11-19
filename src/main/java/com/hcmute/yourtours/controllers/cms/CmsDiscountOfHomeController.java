package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsDiscountOfHomeController;
import com.hcmute.yourtours.factories.discount_of_home.IDiscountOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeInfo;
import com.hcmute.yourtours.models.discount_of_home.models.CreateListDiscountOfHomeModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/discount-of-home")
@Tag(name = "CMS API: DISCOUNT OF HOME", description = "API admin config các giảm giá của căn nhà")
@Transactional
public class CmsDiscountOfHomeController
        extends BaseController<UUID, DiscountOfHomeInfo, DiscountOfHomeDetail>
        implements ICmsDiscountOfHomeController {

    private final IDiscountOfHomeFactory iDiscountOfHomeFactory;

    protected CmsDiscountOfHomeController
            (
                    IDiscountOfHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iDiscountOfHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<DiscountOfHomeDetail>> createModel(FactoryCreateRequest<UUID, DiscountOfHomeDetail> request) {
        return factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> createModelWithList(CreateListDiscountOfHomeModel request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iDiscountOfHomeFactory.createListDiscountOfHome(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
