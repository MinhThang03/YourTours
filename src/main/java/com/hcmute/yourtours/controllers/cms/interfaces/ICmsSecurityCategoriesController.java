package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.security_categories.SecurityCategoriesDetail;
import com.hcmute.yourtours.models.security_categories.SecurityCategoriesInfo;

import java.util.UUID;

public interface ICmsSecurityCategoriesController extends
        ICreateModelController<UUID, SecurityCategoriesDetail>,
        IUpdateModelController<UUID, SecurityCategoriesDetail>,
        IGetDetailByIdController<UUID, SecurityCategoriesDetail>,
        IGetInfoPageController<UUID, SecurityCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
