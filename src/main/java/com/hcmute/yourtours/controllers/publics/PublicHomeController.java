package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicHomeController;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.homes.app.IAppHomesFactory;
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
import com.hcmute.yourtours.models.homes.filter.HomeDetailFilter;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/homes")
@Tag(name = "PUBLIC API: HOMES", description = "API lấy danh sách nhà")
@Transactional
public class PublicHomeController
        extends BaseController<UUID, HomeInfo, HomeDetail>
        implements IPublicHomeController {

    private final IAppHomesFactory iHomesFactory;

    protected PublicHomeController
            (
                    @Qualifier("appHomesFactory") IHomesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    IAppHomesFactory iHomesFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iHomesFactory = iHomesFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFilter
            (
                    HomeFilter filter,
                    Integer number,
                    Integer size
            ) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    @Operation(summary = "Get paging with full filter")
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFullFilter(HomeDetailFilter filter, Integer number, Integer size) {
        try {
            BasePagingResponse<HomeInfo> response = iHomesFactory.getPageWithFullFilter(filter, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
