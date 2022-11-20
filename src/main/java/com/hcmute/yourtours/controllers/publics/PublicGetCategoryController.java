package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPublicGuestCategoryController;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/guest-categories")
@Tag(name = "PUBLIC API: GUEST CATEGORIES", description = "API lấy danh sách loại khách")
@Transactional
public class PublicGetCategoryController
        extends BaseController<UUID, GuestCategoryInfo, GuestCategoryDetail>
        implements IPublicGuestCategoryController {


    protected PublicGetCategoryController(IDataFactory<UUID, GuestCategoryInfo, GuestCategoryDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<GuestCategoryInfo>>> getInfoPageWithFilter(BaseFilter filter, Integer number, Integer size) {
        return getInfoPageWithFilter(filter, number, size);
    }
}
