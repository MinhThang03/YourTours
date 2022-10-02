package com.hcmute.yourtours.factories.guest_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.guest_categories.GuestCategoriesDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoriesInfo;

import java.util.UUID;

public interface IGuestCategoriesFactory extends IDataFactory<UUID, GuestCategoriesInfo, GuestCategoriesDetail> {
}
