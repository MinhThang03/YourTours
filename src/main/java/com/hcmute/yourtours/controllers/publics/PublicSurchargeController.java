package com.hcmute.yourtours.controllers.publics;


import com.hcmute.yourtours.controllers.publics.interfaces.IPublicSurchargeController;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeInfo;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/surcharges")
@Tag(name = "PUBLIC API: SURCHARGE", description = "API lấy danh sách phụ phí được cấu hình của một ngôi nhà")
@Transactional
public class PublicSurchargeController
        extends BaseController<UUID, SurchargeOfHomeInfo, SurchargeOfHomeDetail>
        implements IPublicSurchargeController {

    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;

    protected PublicSurchargeController
            (
                    ISurchargeOfHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    ISurchargeOfHomeFactory iSurchargeOfHomeFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<SurchargeHomeViewModel>>> getInfoPageWithHomeId(UUID homeId, Integer number, Integer size) {
        BasePagingResponse<SurchargeHomeViewModel> response = iSurchargeOfHomeFactory.getPageSurchargeOfHome(homeId, number, size);
        LogContext.push(LogType.RESPONSE, response);
        return iResponseFactory.success(response);
    }
}
