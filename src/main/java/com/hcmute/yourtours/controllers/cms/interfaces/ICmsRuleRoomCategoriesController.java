package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryDetail;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryInfo;

import java.util.UUID;

public interface ICmsRuleRoomCategoriesController extends
        ICreateModelController<UUID, RuleHomeCategoryDetail>,
        IUpdateModelController<UUID, RuleHomeCategoryDetail>,
        IGetDetailByIdController<UUID, RuleHomeCategoryDetail>,
        IGetInfoPageController<UUID, RuleHomeCategoryInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
