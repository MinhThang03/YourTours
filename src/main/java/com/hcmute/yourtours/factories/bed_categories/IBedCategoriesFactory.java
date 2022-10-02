package com.hcmute.yourtours.factories.bed_categories;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoriesInfo;

import java.util.UUID;

public interface IBedCategoriesFactory extends IDataFactory<UUID, BedCategoriesInfo, BedCategoriesDetail> {
}
