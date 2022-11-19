package com.hcmute.yourtours.factories.beds_of_home;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeInfo;
import com.hcmute.yourtours.models.beds_of_home.models.CreateListBedOfHomeDetail;
import com.hcmute.yourtours.models.common.SuccessResponse;

import java.util.UUID;

public interface IBedsOfHomeFactory extends IDataFactory<UUID, BedOfHomeInfo, BedOfHomeDetail> {
    Integer getNumberOfBedWithHomeId(UUID homeId);

    Integer countByRoomHomeIdAndCategoryId(UUID roomHomeId, UUID categoryId);

    SuccessResponse createListModel(CreateListBedOfHomeDetail request) throws InvalidException;

    String getDescriptionNumberBedOfRoom(UUID roomHomeId) throws InvalidException;
}
