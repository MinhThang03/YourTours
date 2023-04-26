package com.hcmute.yourtours.controllers.mobile;

import com.hcmute.yourtours.controllers.mobile.interfaces.IMobileHomeController;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.homes.mobile.IMobileHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mobile/homes")
@Tag(name = "MOBILE API: HOMES", description = "API lấy danh sách nhà")
@Transactional
public class MobileHomeController extends BaseController<UUID, HomeInfo, HomeDetail>
        implements IMobileHomeController {

    private final IMobileHomeFactory iMobileHomeFactory;

    protected MobileHomeController
            (
                    @Qualifier("mobileHomeFactory") IHomesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    IMobileHomeFactory iMobileHomeFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iMobileHomeFactory = iMobileHomeFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFilter(HomeFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getFavoritePage(Integer number, Integer size) {
        try {
            BasePagingResponse<HomeInfo> response = iMobileHomeFactory.getFavoritePage(number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
