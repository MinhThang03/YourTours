package com.hcmute.yourtours.factories.room_categories.cms;

import com.hcmute.yourtours.factories.room_categories.RoomCategoriesFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;
import com.hcmute.yourtours.models.room_categories.filter.RoomCategoryFilter;
import com.hcmute.yourtours.repositories.RoomCategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CmsRoomCategoriesFactory extends RoomCategoriesFactory implements ICmsRoomCategoriesFactory {

    private final IRoomsOfHomeFactory iRoomsOfHomeFactory;

    protected CmsRoomCategoriesFactory(RoomCategoriesRepository repository, IRoomsOfHomeFactory iRoomsOfHomeFactory) {
        super(repository);
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
    }


    @Override
    public BasePagingResponse<RoomCategoryInfo> getPageRoomInHome(RoomCategoryFilter filter, Integer number, Integer size) throws InvalidException {
        BasePagingResponse<RoomCategoryInfo> resultPage = aroundGetPage(filter, number, size);
        List<RoomCategoryInfo> content = new ArrayList<>();

        if (filter.getHomeId() != null) {
            for (RoomCategoryInfo item : resultPage.getContent()) {
                content.add(
                        item.toBuilder()
                                .numberOfHomes(iRoomsOfHomeFactory.countNumberRoomOfHome(filter.getHomeId(), item.getId()))
                                .build()
                );
            }
        }

        return new BasePagingResponse<>(
                content,
                resultPage.getPageNumber(),
                resultPage.getPageSize(),
                resultPage.getTotalElements()
        );
    }
}
