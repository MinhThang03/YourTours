package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IAppHomeController;
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
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.filter.HomeMobileFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/homes")
@Tag(name = "APP API: HOMES", description = "API lấy danh sách nhà")
@Transactional
public class AppHomeController
        extends BaseController<UUID, HomeInfo, HomeDetail>
        implements IAppHomeController {


    private final IAppHomesFactory iAppHomesFactory;


    protected AppHomeController
            (
                    @Qualifier("appHomesFactory") IHomesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    @Qualifier("appHomesFactory") IAppHomesFactory iAppHomesFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iAppHomesFactory = iAppHomesFactory;
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
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFullFilter(HomeMobileFilter filter, Integer number, Integer size) {
        try {
            LogContext.push(LogType.REQUEST, filter);
            BasePagingResponse<HomeInfo> response = iAppHomesFactory.getPageWithProvinceAndAmenity(filter, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageRecommend(String city, Integer number, Integer size) {
        try {
            LogContext.push(LogType.REQUEST, city);
            BasePagingResponse<HomeInfo> response = iAppHomesFactory.getPageRecommend(city, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
