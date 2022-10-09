package com.hcmute.yourtours.factories.images_home;

import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.images_home.ImageHomeDetail;
import com.hcmute.yourtours.models.images_home.ImageHomeInfo;

import java.util.UUID;

public interface IImagesHomeFactory extends IDataFactory<UUID, ImageHomeInfo, ImageHomeDetail> {
}
