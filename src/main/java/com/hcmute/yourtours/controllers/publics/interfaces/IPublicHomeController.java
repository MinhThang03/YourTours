package com.hcmute.yourtours.controllers.publics.interfaces;

import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeDetailFilter;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.models.UserHomeDetailModel;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

public interface IPublicHomeController extends IGetInfoPageController<UUID, HomeInfo, HomeFilter> {


    @GetMapping("page/filter")
    ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFullFilter(
            @ParameterObject @Valid HomeDetailFilter filter,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );

    @GetMapping("{id}/detail")
    ResponseEntity<BaseResponse<UserHomeDetailModel>> getDetailById(@PathVariable UUID id);


    @GetMapping("page/search")
    ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithProvince(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );


}
