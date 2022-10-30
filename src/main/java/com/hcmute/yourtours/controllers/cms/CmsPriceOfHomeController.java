package com.hcmute.yourtours.controllers.cms;


import com.hcmute.yourtours.controllers.cms.interfaces.ICmsPriceOfHomeController;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;
import com.hcmute.yourtours.models.price_of_home.filter.PriceOfHomeFilter;
import com.hcmute.yourtours.models.price_of_home.request.PriceOfHomeCreateRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeWithMonthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/prices")
@Tag(name = "CMS API: PRICES OF HOME", description = "API admin config giá của căn nhà")
@Transactional
public class CmsPriceOfHomeController
        extends BaseController<UUID, PriceOfHomeInfo, PriceOfHomeDetail>
        implements ICmsPriceOfHomeController {

    private final IPriceOfHomeFactory iPriceOfHomeFactory;

    protected CmsPriceOfHomeController(IPriceOfHomeFactory iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        this.iPriceOfHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> createModel(PriceOfHomeCreateRequest request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            SuccessResponse response = iPriceOfHomeFactory.createWithHomeId(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<PriceOfHomeWithMonthResponse>> getWithMonth(@Valid PriceOfHomeFilter filter) {
        LogContext.push(LogType.REQUEST, filter);
        try {
            PriceOfHomeWithMonthResponse response = iPriceOfHomeFactory.getByHomeIdAndMonth(filter);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
