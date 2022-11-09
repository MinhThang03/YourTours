package com.hcmute.yourtours.factories.discount_home_categories;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryInfo;

import java.util.List;
import java.util.UUID;

public interface IDiscountHomeCategoriesFactory extends IDataFactory<UUID, DiscountHomeCategoryInfo, DiscountHomeCategoryDetail> {
    void checkExistsByDiscountCategoryId(UUID categoryId) throws InvalidException;

    List<DiscountHomeCategoryDetail> getDiscountCategoriesActive() throws InvalidException;
}
