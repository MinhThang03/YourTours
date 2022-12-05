package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

public interface ICmsStatisticController {

    @GetMapping("owner")
    ResponseEntity<BaseResponse<OwnerStatistic>> getOwnerStatistic(@ParameterObject @Valid OwnerHomeStatisticFilter filter);
}
