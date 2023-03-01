package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoListController;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;
import com.hcmute.yourtours.models.province.ProvinceProjection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPublicProvinceController extends IGetInfoListController<Long, ProvinceModel> {
    @GetMapping("page")
    ResponseEntity<BaseResponse<BasePagingResponse<ProvinceProjection>>> getPageProvinceSortByNumberBooking(
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
