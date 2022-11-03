package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IAppHomeController;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
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
@RequestMapping("/api/v1/app/homes")
@Tag(name = "APP API: homes", description = "API lấy danh sách nhà")
@Transactional
public class AppHomeController
        extends BaseController<UUID, HomeInfo, HomeDetail>
        implements IAppHomeController {

    protected AppHomeController
            (
                    @Qualifier("appHomesFactory") IHomesFactory iDataFactory,
                    IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
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
}
