package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.models.HostHomeDetailModel;
import com.hcmute.yourtours.models.homes.models.UpdateAddressHomeModel;
import com.hcmute.yourtours.models.homes.models.UpdateBasePriceHomeModel;
import com.hcmute.yourtours.models.homes.models.UpdateBaseProfileHomeModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsHomeController extends
        ICreateModelController<UUID, HomeDetail>,
        IGetInfoPageController<UUID, HomeInfo, HomeFilter> {

    @GetMapping("{id}/detail")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> getDetailById(@PathVariable UUID id);

    @PutMapping("update/homes-profile")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> updateBaseProfile(@RequestBody @Valid FactoryUpdateRequest<UUID, UpdateBaseProfileHomeModel> request);

    @PutMapping("update/homes-price")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> updatePrice(@RequestBody @Valid FactoryUpdateRequest<UUID, UpdateBasePriceHomeModel> request);

    @PutMapping("update/homes-address")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> updateAddress(@RequestBody @Valid FactoryUpdateRequest<UUID, UpdateAddressHomeModel> request);
}
