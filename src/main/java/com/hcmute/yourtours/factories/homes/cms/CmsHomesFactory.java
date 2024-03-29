package com.hcmute.yourtours.factories.homes.cms;

import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.HomesFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.province.IProvinceFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.CmsHomeFilter;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                    IUserFactory iUserFactory,
                    IProvinceFactory iProvinceFactory
            ) {
        super(repository,
                iImagesHomeFactory,
                iRoomsOfHomeFactory,
                iAmenitiesOfHomeFactory,
                iOwnerOfHomeFactory,
                iGetUserFromTokenFactory,
                iItemFavoritesFactory,
                iUserFactory, iProvinceFactory);
        this.iBedsOfHomeFactory = iBedsOfHomeFactory;
    }

    @Override
    protected <F extends BaseFilter> Page<Homes> pageQuery(F filter, Integer number, Integer size) {
        CmsHomeFilter homeFilter = (CmsHomeFilter) filter;
        iGetUserFromTokenFactory.getCurrentUser().ifPresent(userId -> homeFilter.setUserId(UUID.fromString(userId)));
        if (homeFilter.getStatusList() != null && homeFilter.getStatusList().isEmpty()) {
            homeFilter.setStatusList(null);
        }
        return homesRepository.getCmsPageHome
                (
                        homeFilter.getUserId(),
                        homeFilter.getStatusList(),
                        homeFilter.getKeyword(),
                        PageRequest.of(number, size)
                );
    }

    @Override
    public HomeDetail convertToDetail(Homes entity) throws InvalidException {
        return super.convertToDetail(entity).toBuilder()
                .numberOfBed(iBedsOfHomeFactory.getNumberOfBedWithHomeId(entity.getId()))
                .roomsImportant(iRoomsOfHomeFactory.getNumberOfRoomCategoryByHomeId(entity.getId(), true))
                .build();
    }

    @Override
    public HomeInfo convertToInfo(Homes entity) throws InvalidException {
        return super.convertToInfo(entity).toBuilder()
                .numberOfBed(iBedsOfHomeFactory.getNumberOfBedWithHomeId(entity.getId()))
                .roomsImportant(iRoomsOfHomeFactory.getNumberOfRoomCategoryByHomeId(entity.getId(), true))
                .build();
    }
}
