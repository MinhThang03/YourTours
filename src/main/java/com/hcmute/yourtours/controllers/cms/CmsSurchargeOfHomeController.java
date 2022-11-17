package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsSurchargeOfHomeController;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/surcharge-of-home")
@Tag(name = "CMS API: SURCHARGE OF HOME", description = "API admin config các phụ phí của căn nhà")
@Transactional
public class CmsSurchargeOfHomeController
        extends BaseController<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail>
        implements ICmsSurchargeOfHomeController {

    protected CmsSurchargeOfHomeController(IDataFactory<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<SurchargeOfHomeDetail>> createModel(FactoryCreateRequest<UUID, SurchargeOfHomeDetail> request) {
        return factoryCreateModel(request);
    }
}
