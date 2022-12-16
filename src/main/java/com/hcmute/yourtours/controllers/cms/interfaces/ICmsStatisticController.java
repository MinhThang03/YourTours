package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.models.InfoUserBookingModel;
import com.hcmute.yourtours.models.owner_of_home.models.StatisticInfoOwnerModel;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface ICmsStatisticController {

    @GetMapping("owner")
    ResponseEntity<BaseResponse<OwnerStatistic>> getOwnerStatistic(@ParameterObject @Valid OwnerHomeStatisticFilter filter);

    @GetMapping("admin")
    ResponseEntity<BaseResponse<AdminStatistic>> getAdminStatistic(@ParameterObject @Valid AdminHomeStatisticFilter filter);

    @GetMapping("admin/guests")
    ResponseEntity<BaseResponse<BasePagingResponse<InfoUserBookingModel>>> getInfoGuestsBooking
            (
                    @ParameterObject @Valid BaseFilter filter,
                    @RequestParam(defaultValue = "0") Integer number,
                    @RequestParam(defaultValue = "20") Integer size
            );

    @GetMapping("admin/owners")
    ResponseEntity<BaseResponse<BasePagingResponse<StatisticInfoOwnerModel>>> getInfoOwnerBooking
            (
                    @ParameterObject @Valid BaseFilter filter,
                    @RequestParam(defaultValue = "0") Integer number,
                    @RequestParam(defaultValue = "20") Integer size
            );
}
