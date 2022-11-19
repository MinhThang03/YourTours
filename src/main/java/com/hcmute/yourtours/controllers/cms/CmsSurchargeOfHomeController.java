package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsSurchargeOfHomeController;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeInfo;
import com.hcmute.yourtours.models.surcharges_of_home.models.CreateListSurchargeHomeModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/surcharge-of-home")
@Tag(name = "CMS API: SURCHARGE OF HOME", description = "API admin config các phụ phí của căn nhà")
@Transactional
public class CmsSurchargeOfHomeController
        extends BaseController<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail>
        implements ICmsSurchargeOfHomeController {

    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;

    protected CmsSurchargeOfHomeController
            (
                    ISurchargeOfHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        this.iSurchargeOfHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<SurchargeOfHomeDetail>> createModel(FactoryCreateRequest<UUID, SurchargeOfHomeDetail> request) {
        return factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> createModelWithList(@Valid CreateListSurchargeHomeModel request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iSurchargeOfHomeFactory.createListSurchargeOfHome(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
