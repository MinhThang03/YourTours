package com.hcmute.yourtours.factories.rooms_of_home;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.models.rooms_of_home.filter.RoomOfHomeFilter;
import com.hcmute.yourtours.models.rooms_of_home.models.CreateListRoomOfHomeModel;
import com.hcmute.yourtours.models.rooms_of_home.models.NumberOfRoomsModel;
import com.hcmute.yourtours.models.rooms_of_home.models.RoomOfHomeCreateModel;
import com.hcmute.yourtours.models.rooms_of_home.models.RoomOfHomeDetailWithBedModel;

import java.util.List;
import java.util.UUID;

public interface IRoomsOfHomeFactory extends IDataFactory<UUID, RoomOfHomeInfo, RoomOfHomeDetail> {
    void createListWithHomeId(UUID homeId, List<RoomOfHomeCreateModel> listCreate) throws InvalidException;

    List<NumberOfRoomsModel> getNumberOfRoomCategoryByHomeId(UUID homeId, Boolean important);

    BasePagingResponse<RoomOfHomeInfo> getPageRoomOfHomeOfHost(RoomOfHomeFilter filter, Integer number, Integer size) throws InvalidException;

    SuccessResponse createWithListModel(CreateListRoomOfHomeModel request) throws InvalidException;

    Long countNumberRoomOfHome(UUID homeId, UUID categoryId) throws InvalidException;

    List<RoomOfHomeDetailWithBedModel> getRoomHaveConfigBed(UUID homeId);

    List<RoomOfHomeInfo> getAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId) throws InvalidException;
}
