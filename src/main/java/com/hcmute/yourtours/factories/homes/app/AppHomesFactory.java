package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.constant.RoomCategoryIdConstant;
import com.hcmute.yourtours.entities.Homes;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.geo_ip_location.IGeoIPLocationFactory;
import com.hcmute.yourtours.factories.home_view.IHomeViewFactory;
import com.hcmute.yourtours.factories.homes.HomesFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.province.IProvinceFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.geo_ip_location.GeoIPLocation;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeDetailFilter;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.filter.HomeMobileFilter;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppHomesFactory extends HomesFactory implements IAppHomesFactory {

    private final IHomeViewFactory iHomeViewFactory;
    private final IGeoIPLocationFactory iGeoIPLocationFactory;

    protected AppHomesFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IItemFavoritesFactory iItemFavoritesFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IHomeViewFactory iHomeViewFactory,
                    IUserFactory iUserFactory,
                    IProvinceFactory iProvinceFactory,
                    IGeoIPLocationFactory iGeoIPLocationFactory
            ) {
        super(
                repository,
                iImagesHomeFactory,
                iRoomsOfHomeFactory,
                iAmenitiesOfHomeFactory,
                iOwnerOfHomeFactory,
                iGetUserFromTokenFactory,
                iItemFavoritesFactory,
                iUserFactory, iProvinceFactory);
        this.iHomeViewFactory = iHomeViewFactory;
        this.iGeoIPLocationFactory = iGeoIPLocationFactory;
    }


    @Override
    public HomeInfo convertToInfo(Homes entity) throws InvalidException {
        HomeInfo info = super.convertToInfo(entity);
        return info.toBuilder()
                .isFavorite(checkIsFavorite(entity.getId()))
                .build();
    }

    @Override
    public HomeDetail convertToDetail(Homes entity) throws InvalidException {
        HomeDetail detail = super.convertToDetail(entity);
        return detail.toBuilder()
                .isFavorite(checkIsFavorite(entity.getId()))
                .build();
    }

    @Override
    protected <F extends BaseFilter> void preGetDetail(UUID id, F filter) throws InvalidException {
        HomeDetail detail = convertToDetail(findByHomeId(id));
        detail.setView(detail.getView() + 1);
        updateModel(id, detail);

        iHomeViewFactory.increaseView(id);
    }

    @Override
    public SuccessResponse handleFavorites(ItemFavoritesDetail detail) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        checkExistsByHomeId((detail.getHomeId()));
        detail.setUserId(userId);
        boolean result = iItemFavoritesFactory.handleFavorites(detail);
        return SuccessResponse.builder()
                .success(result)
                .build();
    }

    @Override
    public BasePagingResponse<HomeInfo> getFavoritesListOfCurrentUser(Integer number, Integer size) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        Page<Homes> page = homesRepository.getFavoritesListByUserId
                (
                        userId,
                        CommonStatusEnum.ACTIVE.name(),
                        PageRequest.of(number, size)
                );

        return new BasePagingResponse<>(
                convertList(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }


    @Override
    public BasePagingResponse<HomeInfo> getPageWithFullFilter(HomeDetailFilter filter, Integer number, Integer size) throws InvalidException {
        int lenght = 0;
        if (filter.getAmenities() != null) {
            lenght = filter.getAmenities().size();
        }

        if (filter.getAmenities() == null || filter.getAmenities().isEmpty()) {
            filter.setAmenities(List.of(UUID.randomUUID()));
        }

        Page<Homes> pageEntity = homesRepository.getPageWithFullFilter
                (
                        filter.getAmenityId(),
                        filter.getPriceFrom(),
                        filter.getPriceTo(),
                        filter.getNumberOfBed(),
                        filter.getNumberOfBedRoom(),
                        filter.getNumberOfBathRoom(),
                        filter.getAmenities(),
                        lenght,
                        RoomCategoryIdConstant.BED_ROOM_CATEGORY_ID,
                        RoomCategoryIdConstant.BATH_ROOM_CATEGORY_ID,
                        filter.getProvinceCode(),
                        PageRequest.of(number, size)
                );

        return new BasePagingResponse<>(
                convertList(pageEntity.getContent()),
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalElements()
        );
    }

    @Override
    public BasePagingResponse<HomeInfo> getPageWithProvinceAndAmenity(HomeMobileFilter filter, Integer number, Integer size) throws InvalidException {
        Page<Homes> pageEntity = homesRepository.getPageWithProvinceAndAmenity
                (
                        filter.getProvince(),
                        filter.getAmenityId(),
                        PageRequest.of(number, size)
                );

        return new BasePagingResponse<>(
                convertList(pageEntity.getContent()),
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalElements()
        );
    }

    @Override
    public BasePagingResponse<HomeInfo> getPageRecommend(String city, Integer number, Integer size) throws InvalidException {

        if (city == null || city.isBlank()) {
            GeoIPLocation location = iGeoIPLocationFactory.getLocationByCurrentIp();
            if (location != null) {
                city = location.getCityName();
            }
        }

        Page<Homes> pageEntity = homesRepository.getPageRecommend(city, PageRequest.of(number, size));

        return new BasePagingResponse<>(
                convertList(pageEntity.getContent()),
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalElements()
        );
    }


    protected boolean checkIsFavorite(UUID homeId) {
        Optional<String> userId = iGetUserFromTokenFactory.getCurrentUser();
        if (userId.isEmpty()) {
            return false;
        }

        Optional<Homes> home = homesRepository.findIsFavoriteByHomeIdAndUserId(homeId, UUID.fromString(userId.get()));
        return home.isPresent();
    }


    @Override
    protected <F extends BaseFilter> Page<Homes> pageQuery(F filter, Integer number, Integer size) {
        HomeFilter homeFilter = (HomeFilter) filter;
        return homesRepository.findPageWithFilter
                (
                        homeFilter.getUserId(),
                        homeFilter.getSort() == null ? null : homeFilter.getSort().name(),
                        CommonStatusEnum.ACTIVE.name(),
                        PageRequest.of(number, size)
                );
    }
}
