package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsAmenityCategoriesController;
import com.hcmute.yourtours.factories.amenity_categories.IAmenityCategoryHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryHomeDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/amenity-categories")
@Tag(name = "CMS API: AMENITY CATEGORIES", description = "API admin config các loại tiện ích")
@Transactional
public class CmsAmenityCategoriesController
        extends BaseController<UUID, AmenityCategoryInfo, AmenityCategoryDetail>
        implements ICmsAmenityCategoriesController {

    private final IAmenityCategoryHomeFactory iAmenityCategoryHomeFactory;

    protected CmsAmenityCategoriesController(
            IDataFactory<UUID, AmenityCategoryInfo, AmenityCategoryDetail> iDataFactory,
            IResponseFactory iResponseFactory, IAmenityCategoryHomeFactory iAmenityCategoryHomeFactory) {
        super(iDataFactory, iResponseFactory);
        this.iAmenityCategoryHomeFactory = iAmenityCategoryHomeFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<AmenityCategoryDetail>> createModel(FactoryCreateRequest<UUID, AmenityCategoryDetail> request) {
        return super.factoryCreateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(UUID id) {
        return super.factoryDeleteWithFilter(id, null);
    }

    @Override
    public ResponseEntity<BaseResponse<FactoryGetResponse<UUID, AmenityCategoryDetail>>> getDetailById(UUID id) {
        return super.factoryGetDetailById(id);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<AmenityCategoryInfo>>> getInfoPageWithFilter(SimpleFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<AmenityCategoryDetail>> updateModel(FactoryUpdateRequest<UUID, AmenityCategoryDetail> request) {
        return super.factoryUpdateModel(request);
    }

    @Override
    public ResponseEntity<BaseResponse<AmenityCategoryHomeDetail>> getDetailByIdAndHomeId(UUID id, UUID homeId) {
        try {
            AmenityCategoryHomeDetail response = iAmenityCategoryHomeFactory.getDetailWithListChild(id, homeId);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }
}
