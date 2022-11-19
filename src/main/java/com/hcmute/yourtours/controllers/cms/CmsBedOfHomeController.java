package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.ICmsBedOfHomeController;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeInfo;
import com.hcmute.yourtours.models.beds_of_home.models.CreateListBedOfHomeDetail;
import com.hcmute.yourtours.models.common.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/bed-of-home")
@Tag(name = "CMS API: BED OF HOME", description = "API admin config các giường của ngôi nhà")
@Transactional
public class CmsBedOfHomeController
        extends BaseController<UUID, BedOfHomeInfo, BedOfHomeDetail>
        implements ICmsBedOfHomeController {

    private final IBedsOfHomeFactory iBedsOfHomeFactory;

    protected CmsBedOfHomeController
            (
                    IBedsOfHomeFactory iDataFactory,
                    IResponseFactory iResponseFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iBedsOfHomeFactory = iDataFactory;
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> createModelWithList(CreateListBedOfHomeDetail request) {
        try {
            LogContext.push(LogType.REQUEST, request);
            SuccessResponse response = iBedsOfHomeFactory.createListModel(request);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
