package com.hcmute.yourtours.factories.bed_categories;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.bed_categories.BedCategoryDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;

import java.util.UUID;

public interface IBedCategoriesFactory extends IDataFactory<UUID, BedCategoryInfo, BedCategoryDetail> {
    void checkExistsByBedCategoryId(UUID bedCategoryId) throws InvalidException;
}
