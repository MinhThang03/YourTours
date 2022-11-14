package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsAmenitiesOfHomeController;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeDetail;
import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/amenities-of-homes")
@Tag(name = "CMS API: AMENITIES OF HOME", description = "API admin config các tiện ích cho nha")
@Transactional
public class CmsAmenitiesOfHomeController
        extends BaseController<UUID, AmenityOfHomeInfo, AmenityOfHomeDetail>
        implements ICmsAmenitiesOfHomeController {


    protected CmsAmenitiesOfHomeController(IDataFactory<UUID, AmenityOfHomeInfo, AmenityOfHomeDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<AmenityOfHomeDetail>> createModel(FactoryCreateRequest<UUID, AmenityOfHomeDetail> request) {
        return factoryCreateModel(request);
    }
}
