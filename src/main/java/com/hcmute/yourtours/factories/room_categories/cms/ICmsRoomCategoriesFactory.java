package com.hcmute.yourtours.factories.room_categories.cms;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;
import com.hcmute.yourtours.models.room_categories.filter.RoomCategoryFilter;

public interface ICmsRoomCategoriesFactory {

    BasePagingResponse<RoomCategoryInfo> getPageRoomInHome(RoomCategoryFilter filter, Integer number, Integer size) throws InvalidException;

}
