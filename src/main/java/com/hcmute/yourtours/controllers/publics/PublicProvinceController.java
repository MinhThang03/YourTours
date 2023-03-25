package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicProvinceController;
import com.hcmute.yourtours.factories.province.IProvinceFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;
import com.hcmute.yourtours.models.province.ProvinceProjection;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/public/app/provinces")
@Tag(name = "PUBLIC API: PROVINCE", description = "API lấy danh sách tỉnh")
@Transactional
public class PublicProvinceController extends BaseController<Long, ProvinceModel, ProvinceModel> implements IPublicProvinceController {

    private final IProvinceFactory iProvinceFactory;

    protected PublicProvinceController(IProvinceFactory iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
        this.iProvinceFactory = iDataFactory;
    }


    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<ProvinceProjection>>> getPageProvinceSortByNumberBooking(Integer number, Integer size) {
        BasePagingResponse<ProvinceProjection> response = iProvinceFactory.getProvinceSortByNumberBooking(number, size);
        LogContext.push(LogType.RESPONSE, response);
        return iResponseFactory.success(response);
    }


    @Override
    public ResponseEntity<BaseResponse<List<ProvinceModel>>> getInfoList() {
        return factoryGetInfoListWithFilter(null);
    }
}
