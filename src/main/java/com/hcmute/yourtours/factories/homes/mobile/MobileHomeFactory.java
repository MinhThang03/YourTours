package com.hcmute.yourtours.factories.homes.mobile;

import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.geo_ip_location.IGeoIPLocationFactory;
import com.hcmute.yourtours.factories.homes.app.AppHomesFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.province.IProvinceFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.user_evaluate.IUserEvaluateFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeFilter;
import com.hcmute.yourtours.models.homes.projections.MobileHomeProjection;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MobileHomeFactory extends AppHomesFactory implements IMobileHomeFactory {

    protected MobileHomeFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IItemFavoritesFactory iItemFavoritesFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    @Qualifier("appUserEvaluateFactory") IUserEvaluateFactory iUserEvaluateFactory,
                    @Qualifier("appBookHomeFactory") IBookHomeFactory iBookHomeFactory,
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
                iItemFavoritesFactory,
                iGetUserFromTokenFactory,
                iUserEvaluateFactory,
                iBookHomeFactory,
                iUserFactory,
                iProvinceFactory,
                iGeoIPLocationFactory
        );
    }


    @Override
    protected <F extends BaseFilter> BasePagingResponse<HomeInfo> aroundGetPage(F filter, Integer number, Integer size) throws InvalidException {
        HomeFilter homeFilter = (HomeFilter) filter;

        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        Page<MobileHomeProjection> projections = homesRepository.getPageForMobile
                (
                        CommonStatusEnum.ACTIVE.name(),
                        homeFilter.getSort() == null ? null : homeFilter.getSort().name(),
                        userId,
                        PageRequest.of(number, size)
                );

        List<HomeInfo> homeInfo = new ArrayList<>();

        projections.getContent().forEach(item -> homeInfo.add(homeProjectionToInfo(item)));

        return new BasePagingResponse<>(
                homeInfo,
                projections.getNumber(),
                projections.getSize(),
                projections.getTotalElements()
        );
    }


    @Override
    public BasePagingResponse<HomeInfo> getFavoritePage(Integer number, Integer size) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        Page<MobileHomeProjection> projections = homesRepository.getPageFavoriteMobile
                (
                        CommonStatusEnum.ACTIVE.name(),
                        userId,
                        PageRequest.of(number, size)
                );

        List<HomeInfo> homeInfo = new ArrayList<>();

        projections.getContent().forEach(item -> homeInfo.add(homeProjectionToInfo(item)));

        return new BasePagingResponse<>(
                homeInfo,
                projections.getNumber(),
                projections.getSize(),
                projections.getTotalElements()
        );
    }

    private HomeInfo homeProjectionToInfo(MobileHomeProjection projection) {
        return HomeInfo.builder()
                .id(projection.getId())
                .costPerNightDefault(projection.getCostPerNightDefault())
                .name(projection.getName())
                .view(projection.getView())
                .numberOfBooking(projection.getNumberOfBooking())
                .favorite(projection.getFavorite())
                .numberOfReviews(projection.getNumberOfReview())
                .averageRate(projection.getAverageRate())
                .provinceName(projection.getProvince())
                .isFavorite(projection.getIsFavorite())
                .thumbnail(projection.getThumbnail())
                .build();
    }
}
