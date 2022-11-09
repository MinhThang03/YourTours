package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.HomesFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user_evaluate.IUserEvaluateFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppHomesFactory extends HomesFactory implements IAppHomesFactory {

    private final IUserEvaluateFactory iUserEvaluateFactory;
    private final IBookHomeFactory iBookHomeFactory;

    protected AppHomesFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IItemFavoritesFactory iItemFavoritesFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IUserEvaluateFactory iUserEvaluateFactory,
                    IBookHomeFactory iBookHomeFactory
            ) {
        super(
                repository,
                iImagesHomeFactory,
                iRoomsOfHomeFactory,
                iAmenitiesOfHomeFactory,
                iOwnerOfHomeFactory,
                iGetUserFromTokenFactory,
                iItemFavoritesFactory
        );
        this.iUserEvaluateFactory = iUserEvaluateFactory;
        this.iBookHomeFactory = iBookHomeFactory;
    }

    @Override
    protected <F extends BaseFilter> void preGetDetail(UUID id, F filter) throws InvalidException {
        HomeDetail detail = convertToDetail(findByHomeId(id));
        detail.setView(detail.getView() + 1);
        updateModel(id, detail);
    }

    @Override
    public SuccessResponse handleFavorites(ItemFavoritesDetail detail) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        checkExistsByHomeId((detail.getHomeId()));
        detail.setUserId(userId);
        iItemFavoritesFactory.handleFavorites(detail);
        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public BasePagingResponse<HomeInfo> getFavoritesListOfCurrentUser(Integer number, Integer size) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        Page<HomesCommand> page = homesRepository.getFavoritesListByUserId
                (
                        userId,
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
    public HomeDetail convertToDetail(HomesCommand entity) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        HomeDetail detail = super.convertToDetail(entity);
        return detail.toBuilder()
                .isBooked(iBookHomeFactory.existByUserIdAndHomeId(userId, entity.getHomeId()))
                .build();
    }

    @Override
    public HomeDetail createUserEvaluate(UserEvaluateDetail evaluateDetail) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        if (!iBookHomeFactory.existByUserIdAndHomeId(userId, evaluateDetail.getHomeId())) {
            throw new InvalidException(YourToursErrorCode.USER_NOT_BOOKING_HOME);
        }

        checkExistsByHomeId(evaluateDetail.getHomeId());
        evaluateDetail.setUserId(userId);
        UserEvaluateDetail userEvaluateDetail = iUserEvaluateFactory.createModel(evaluateDetail);
        HomeDetail homeDetail = getDetailModel(evaluateDetail.getHomeId(), null);

        if (userEvaluateDetail.getPoint() == null) {
            return homeDetail;
        }

        homeDetail.setNumberOfReviews(homeDetail.getNumberOfReviews() + 1);
        homeDetail.setAverageRate(iUserEvaluateFactory.getAverageRateOfHome(homeDetail.getId()));
        return updateModel(homeDetail.getId(), homeDetail);
    }

}