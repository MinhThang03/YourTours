package com.hcmute.yourtours.factories.guest_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryInfo;

import java.util.UUID;

public interface IGuestCategoriesFactory extends IDataFactory<UUID, GuestCategoryInfo, GuestCategoryDetail> {
}
