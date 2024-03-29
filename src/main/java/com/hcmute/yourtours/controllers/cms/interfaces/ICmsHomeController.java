package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsHomeController extends
        ICreateModelController<UUID, HomeDetail>,
        IGetInfoPageController<UUID, HomeInfo, CmsHomeFilter> {

    @GetMapping("{id}/detail")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> getDetailById(@PathVariable UUID id);

    @PutMapping("update/homes-profile")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> updateBaseProfile(@RequestBody @Valid FactoryUpdateRequest<UUID, UpdateBaseProfileHomeModel> request);

    @PutMapping("update/homes-price")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> updatePrice(@RequestBody @Valid FactoryUpdateRequest<UUID, UpdateBasePriceHomeModel> request);

    @PutMapping("update/homes-address")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> updateAddress(@RequestBody @Valid FactoryUpdateRequest<UUID, UpdateAddressHomeModel> request);

    @GetMapping("admin/pages")
    ResponseEntity<BaseResponse<BasePagingResponse<CmsHomeInfo>>> getPageWithRoleAdmin(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size);

    @PutMapping("update/status")
    @Operation(summary = "Đổi trạng thái của nhà")
    ResponseEntity<BaseResponse<UpdateHomeStatusResponse>> updateStatus(@RequestBody @Valid UpdateHomeStatusRequest request);
}
