package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicPriceOfHomeController;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/prices")
@Tag(name = "PUBLIC API: PRICE", description = "API lấy thông tin giá nhà")
@Transactional
public class PublicPriceOfHomeController
        extends BaseController<UUID, PriceOfHomeInfo, PriceOfHomeDetail>
        implements IPublicPriceOfHomeController {

    private final IPriceOfHomeFactory iPriceOfHomeFactory;

    protected PublicPriceOfHomeController
            (
                    IPriceOfHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        this.iPriceOfHomeFactory = iDataFactory;
    }

    @Override
    @Operation(summary = "Lấy tổng số tiền ở lại từ ngày bắt đầu đến ngày kết thúc")
    public ResponseEntity<BaseResponse<PriceOfHomeResponse>> getPriceByHomeId(GetPriceOfHomeRequest filter) {
        try {
            PriceOfHomeResponse response = iPriceOfHomeFactory.getCostBetweenDay(filter);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
