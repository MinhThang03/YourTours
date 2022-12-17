package com.hcmute.yourtours.factories.homes.cms;

import com.hcmute.yourtours.entities.HomesCommand;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.HomesFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CmsHomesFactory extends HomesFactory {

    private final IBedsOfHomeFactory iBedsOfHomeFactory;

    protected CmsHomesFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IItemFavoritesFactory iItemFavoritesFactory,
                    IBedsOfHomeFactory iBedsOfHomeFactory,
                    IUserFactory iUserFactory
            ) {
        super(repository,
                iImagesHomeFactory,
                iRoomsOfHomeFactory,
                iAmenitiesOfHomeFactory,
                iOwnerOfHomeFactory,
                iGetUserFromTokenFactory,
                iItemFavoritesFactory,
                iUserFactory);
        this.iBedsOfHomeFactory = iBedsOfHomeFactory;
    }

    @Override
    protected <F extends BaseFilter> Page<HomesCommand> pageQuery(F filter, Integer number, Integer size) {
        HomeFilter homeFilter = (HomeFilter) filter;
        iGetUserFromTokenFactory.getCurrentUser().ifPresent(userId -> homeFilter.setUserId(UUID.fromString(userId)));
        return super.pageQuery(homeFilter, number, size);
    }

    @Override
    public HomeDetail convertToDetail(HomesCommand entity) throws InvalidException {
        return super.convertToDetail(entity).toBuilder()
                .numberOfBed(iBedsOfHomeFactory.getNumberOfBedWithHomeId(entity.getHomeId()))
                .roomsImportant(iRoomsOfHomeFactory.getNumberOfRoomCategoryByHomeId(entity.getHomeId(), true))
                .build();
    }

    @Override
    public HomeInfo convertToInfo(HomesCommand entity) throws InvalidException {
        return super.convertToInfo(entity).toBuilder()
                .numberOfBed(iBedsOfHomeFactory.getNumberOfBedWithHomeId(entity.getHomeId()))
                .roomsImportant(iRoomsOfHomeFactory.getNumberOfRoomCategoryByHomeId(entity.getHomeId(), true))
                .build();
    }
}
