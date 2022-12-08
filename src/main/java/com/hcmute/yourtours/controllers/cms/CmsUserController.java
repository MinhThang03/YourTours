package com.hcmute.yourtours.controllers.cms;

import com.hcmute.yourtours.controllers.cms.interfaces.IUserController;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.models.user.UserInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cms/user")
@Tag(name = "CMS API: USER", description = "Quan ly nguoi dung")
@Transactional
public class CmsUserController
        extends BaseController<UUID, UserInfo, UserDetail>
        implements IUserController {

    protected CmsUserController(IDataFactory<UUID, UserInfo, UserDetail> iDataFactory, IResponseFactory iResponseFactory) {
        super(iDataFactory, iResponseFactory);
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<UserInfo>>> getInfoPageWithFilter(BaseFilter filter, Integer number, Integer size) {
        return factoryGetInfoPageWithFilter(filter, number, size);
    }
}
