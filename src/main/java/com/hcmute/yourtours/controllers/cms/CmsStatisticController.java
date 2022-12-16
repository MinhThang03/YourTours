package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsStatisticController;
import com.hcmute.yourtours.factories.booking.cms.ICmsBookHomeFactory;
import com.hcmute.yourtours.factories.statistic.admin.IAdminStatisticFactory;
import com.hcmute.yourtours.factories.statistic.host.IOwnerStatisticFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.models.InfoUserBookingModel;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminStatistic;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cms/statistic")
@Tag(name = "CMS API: STATISTIC", description = "API thong ke")
@Transactional
public class CmsStatisticController implements ICmsStatisticController {

    private final IOwnerStatisticFactory iOwnerStatisticFactory;
    private final IAdminStatisticFactory iAdminStatisticFactory;
    private final IResponseFactory iResponseFactory;
    private final ICmsBookHomeFactory iCmsBookHomeFactory;

    public CmsStatisticController
            (
                    IOwnerStatisticFactory iOwnerStatisticFactory,
                    IAdminStatisticFactory iAdminStatisticFactory,
                    IResponseFactory iResponseFactory,
                    ICmsBookHomeFactory iCmsBookHomeFactory
            ) {
        this.iOwnerStatisticFactory = iOwnerStatisticFactory;
        this.iAdminStatisticFactory = iAdminStatisticFactory;
        this.iResponseFactory = iResponseFactory;
        this.iCmsBookHomeFactory = iCmsBookHomeFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<OwnerStatistic>> getOwnerStatistic(OwnerHomeStatisticFilter filter) {
        try {
            LogContext.push(LogType.REQUEST, filter);
            OwnerStatistic response = iOwnerStatisticFactory.statistic(filter);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<AdminStatistic>> getAdminStatistic(@Valid AdminHomeStatisticFilter filter) {
        try {
            LogContext.push(LogType.REQUEST, filter);
            AdminStatistic response = iAdminStatisticFactory.statistic(filter);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<InfoUserBookingModel>>> getInfoGuestsBooking(BaseFilter filter, Integer number, Integer size) {
        LogContext.push(LogType.REQUEST, filter);
        BasePagingResponse<InfoUserBookingModel> response = iCmsBookHomeFactory.getStatisticInfoUserBooking(filter, number, size);
        LogContext.push(LogType.RESPONSE, response);
        return iResponseFactory.success(response);
    }
}
