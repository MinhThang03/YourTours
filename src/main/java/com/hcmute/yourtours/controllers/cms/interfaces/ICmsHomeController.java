package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.ICreateModelController;
import com.hcmute.yourtours.libs.controller.IGetInfoPageController;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.models.HostHomeDetailModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface ICmsHomeController extends
        ICreateModelController<UUID, HomeDetail>,
        IGetInfoPageController<UUID, HomeInfo, HomeFilter> {

    @GetMapping("{id}/detail")
    ResponseEntity<BaseResponse<HostHomeDetailModel>> getDetailById(@PathVariable UUID id);
}