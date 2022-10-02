package com.hcmute.yourtours.factories.security_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.security_categories.SecurityCategoriesDetail;
import com.hcmute.yourtours.models.security_categories.SecurityCategoriesInfo;

import java.util.UUID;

public interface ISecurityCategoriesFactory extends IDataFactory<UUID, SecurityCategoriesInfo, SecurityCategoriesDetail> {
}
