package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsHomeController;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.homes.cms.ICmsHandleViewHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.CmsHomeInfo;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.CmsHomeFilter;
import com.hcmute.yourtours.models.homes.models.HostHomeDetailModel;
import com.hcmute.yourtours.models.homes.models.UpdateAddressHomeModel;
import com.hcmute.yourtours.models.homes.models.UpdateBasePriceHomeModel;
import com.hcmute.yourtours.models.homes.models.UpdateBaseProfileHomeModel;
import com.hcmute.yourtours.models.homes.requests.UpdateHomeStatusRequest;
import com.hcmute.yourtours.models.homes.responses.UpdateHomeStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/homes")
@Tag(name = "CMS API: HOMES", description = "API admin config nhà")
@Transactional
public class CmsHomeController
        extends BaseController<UUID, HomeInfo, HomeDetail>
        implements ICmsHomeController {

    private final ICmsHandleViewHomeFactory iCmsHandleViewHomeFactory;
    private final IHomesFactory iHomesFactory;

    protected CmsHomeController
            (
                    @Qualifier("cmsHomesFactory") IHomesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    ICmsHandleViewHomeFactory iCmsHandleViewHomeFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iCmsHandleViewHomeFactory = iCmsHandleViewHomeFactory;
        this.iHomesFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<HomeDetail>> createModel(FactoryCreateRequest<UUID, HomeDetail> request) {
        return super.factoryCreateModel(request);
    }


    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getInfoPageWithFilter(CmsHomeFilter filter, Integer number, Integer size) {
        return super.factoryGetInfoPageWithFilter(filter, number, size);
    }

    @Override
    public ResponseEntity<BaseResponse<HostHomeDetailModel>> getDetailById(UUID id) {
        try {
            LogContext.push(LogType.REQUEST, id);
            HostHomeDetailModel response = iCmsHandleViewHomeFactory.getDetailByHomeId(id);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }


    @Override
    @Operation(summary = "Chỉnh sửa địa chỉ")
    public ResponseEntity<BaseResponse<HostHomeDetailModel>> updateAddress(FactoryUpdateRequest<UUID, UpdateAddressHomeModel> request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            HostHomeDetailModel response = iCmsHandleViewHomeFactory.updateAddress(request.getId(), request.getData());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<CmsHomeInfo>>> getPageWithRoleAdmin(String keyword, Integer number, Integer size) {
        try {
            BasePagingResponse<CmsHomeInfo> response = iHomesFactory.getPageWithRoleAdmin(keyword, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UpdateHomeStatusResponse>> updateStatus(UpdateHomeStatusRequest request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            UpdateHomeStatusResponse response = iHomesFactory.updateHomeStatus(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<HostHomeDetailModel>> updateBaseProfile(FactoryUpdateRequest<UUID, UpdateBaseProfileHomeModel> request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            HostHomeDetailModel response = iCmsHandleViewHomeFactory.updateProfile(request.getId(), request.getData());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    @Operation(summary = "Chỉnh sửa giá tiền của nhà")
    public ResponseEntity<BaseResponse<HostHomeDetailModel>> updatePrice(FactoryUpdateRequest<UUID, UpdateBasePriceHomeModel> request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            HostHomeDetailModel response = iCmsHandleViewHomeFactory.updatePrice(request.getId(), request.getData());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
