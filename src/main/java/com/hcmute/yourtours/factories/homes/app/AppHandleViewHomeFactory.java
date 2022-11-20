package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.enums.EvaluateFilterTypeEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.user_evaluate.IUserEvaluateFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.models.UserHomeDetailModel;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.models.user_evaluate.filter.EvaluateFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppHandleViewHomeFactory implements IAppHandleViewHomeFactory {
    private final IHomesFactory iHomesFactory;
    private final IBookHomeFactory iBookHomeFactory;
    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;
    private final IUserEvaluateFactory iUserEvaluateFactory;
    private final IItemFavoritesFactory iItemFavoritesFactory;
    private final IRoomsOfHomeFactory iRoomsOfHomeFactory;

    private final IOwnerOfHomeFactory iOwnerOfHomeFactory;

    public AppHandleViewHomeFactory(
            @Qualifier("appHomesFactory") IHomesFactory iHomesFactory,
            @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory,
            IGetUserFromTokenFactory iGetUserFromTokenFactory,
            IUserEvaluateFactory iUserEvaluateFactory,
            IItemFavoritesFactory iItemFavoritesFactory,
            IRoomsOfHomeFactory iRoomsOfHomeFactory,
            IOwnerOfHomeFactory iOwnerOfHomeFactory
    ) {
        this.iHomesFactory = iHomesFactory;
        this.iBookHomeFactory = iBookHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
        this.iUserEvaluateFactory = iUserEvaluateFactory;
        this.iItemFavoritesFactory = iItemFavoritesFactory;
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
    }

    @Override
    public UserHomeDetailModel getDetailByHomeId(UUID homeId) throws InvalidException {

        try {
            HomeDetail homeDetail = iHomesFactory.getDetailModel(homeId, null);
            BasePagingResponse<UserEvaluateInfo> evaluates = iUserEvaluateFactory.getInfoPage
                    (
                            EvaluateFilter.builder()
                                    .typeFilter(EvaluateFilterTypeEnum.COMMENT)
                                    .homeId(homeId)
                                    .build(),
                            0,
                            20);

            UserHomeDetailModel result = UserHomeDetailModel.builder()
                    .homeDetail(homeDetail)
                    .evaluates(evaluates)
                    .dateIsBooked(getDatesIdBooked(homeId))
                    .ownerName(iOwnerOfHomeFactory.getMainOwnerOfHome(homeId))
                    .rooms(iRoomsOfHomeFactory.getRoomHaveConfigBed(homeId))
                    .build();

            Optional<String> userId = iGetUserFromTokenFactory.getCurrentUser();
            if (userId.isEmpty()) {
                return result;
            }

            return result.toBuilder()
                    .isBooked(iBookHomeFactory.existByUserIdAndHomeId(UUID.fromString(userId.get()), homeId))
                    .isFavorite(iItemFavoritesFactory.existByUserIdAndHomeId(UUID.fromString(userId.get()), homeId))
                    .build();
        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidException(YourToursErrorCode.CONVERT_TO_UUID_IS_ERROR);
        }

    }

    private List<LocalDate> getDatesIdBooked(UUID homeId) {
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusMonths(1);
        List<MonthAndYearModel> models = new ArrayList<>();
        models.add(MonthAndYearModel.builder()
                .month(now.getMonthValue())
                .year(now.getYear())
                .build());

        models.add(MonthAndYearModel.builder()
                .month(end.getMonthValue())
                .year(end.getYear())
                .build());

        return iBookHomeFactory.getDatesIsBooked(models, homeId);
    }
}
