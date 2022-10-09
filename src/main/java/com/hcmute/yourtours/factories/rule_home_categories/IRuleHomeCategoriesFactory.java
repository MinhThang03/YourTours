package com.hcmute.yourtours.factories.rule_home_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryDetail;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryInfo;

import java.util.UUID;

public interface IRuleHomeCategoriesFactory extends IDataFactory<UUID, RuleHomeCategoryInfo, RuleHomeCategoryDetail> {
}
