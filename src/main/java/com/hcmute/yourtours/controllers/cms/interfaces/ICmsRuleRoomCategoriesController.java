package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.filter.SimpleFilter;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoriesDetail;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoriesInfo;

import java.util.UUID;

public interface ICmsRuleRoomCategoriesController extends
        ICreateModelController<UUID, RuleHomeCategoriesDetail>,
        IUpdateModelController<UUID, RuleHomeCategoriesDetail>,
        IGetDetailByIdController<UUID, RuleHomeCategoriesDetail>,
        IGetInfoPageController<UUID, RuleHomeCategoriesInfo, SimpleFilter>,
        IDeleteModelByIdController<UUID> {
}
