package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsStatisticController;
import com.hcmute.yourtours.factories.statistic.host.IOwnerStatisticFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.statistic.host.filter.OwnerHomeStatisticFilter;
import com.hcmute.yourtours.models.statistic.host.models.OwnerStatistic;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cms/statistic")
@Tag(name = "CMS API: STATISTIC", description = "API thong ke")
@Transactional
public class CmsStatisticController implements ICmsStatisticController {

    private final IOwnerStatisticFactory iOwnerStatisticFactory;
    private final IResponseFactory iResponseFactory;

    public CmsStatisticController(IOwnerStatisticFactory iOwnerStatisticFactory, IResponseFactory iResponseFactory) {
        this.iOwnerStatisticFactory = iOwnerStatisticFactory;
        this.iResponseFactory = iResponseFactory;
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
}
