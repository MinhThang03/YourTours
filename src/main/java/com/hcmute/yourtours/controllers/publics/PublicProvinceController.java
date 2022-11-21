package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicProvinceController;
import com.hcmute.yourtours.factories.province.IProvinceFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/public/app/provinces")
@Tag(name = "PUBLIC API: PROVINCE", description = "API lấy danh sách tỉnh")
@Transactional
public class PublicProvinceController implements IPublicProvinceController {

    private final IResponseFactory iResponseFactory;
    private final IProvinceFactory iProvinceFactory;

    public PublicProvinceController(
            IResponseFactory iResponseFactory,
            IProvinceFactory iProvinceFactory) {
        this.iResponseFactory = iResponseFactory;
        this.iProvinceFactory = iProvinceFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<ProvinceModel>>> getInfoPageWithFilter(Integer number, Integer size) {
        BasePagingResponse<ProvinceModel> response = iProvinceFactory.getListProvinceWithFilter(number, size);
        LogContext.push(LogType.RESPONSE, response);
        return iResponseFactory.success(response);
    }
}
