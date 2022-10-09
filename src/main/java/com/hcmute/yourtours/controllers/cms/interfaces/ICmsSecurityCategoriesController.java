package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.security_categories.SecurityCategoryDetail;
import com.hcmute.yourtours.models.security_categories.SecurityCategoryInfo;

import java.util.UUID;

public interface ICmsSecurityCategoriesController extends
        ICreateModelController<UUID, SecurityCategoryDetail>,
        IUpdateModelController<UUID, SecurityCategoryDetail>,
        IGetDetailByIdController<UUID, SecurityCategoryDetail>,
        IGetInfoPageController<UUID, SecurityCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
