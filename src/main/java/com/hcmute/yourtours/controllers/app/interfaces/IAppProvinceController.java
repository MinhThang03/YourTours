package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IAppProvinceController {
    @GetMapping("page")
    ResponseEntity<BaseResponse<BasePagingResponse<ProvinceModel>>> getInfoPageWithFilter(
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
