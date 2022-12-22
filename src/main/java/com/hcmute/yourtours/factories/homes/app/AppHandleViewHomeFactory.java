package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.constant.RoomCategoryIdConstant;
import com.hcmute.yourtours.enums.EvaluateFilterTypeEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.amenities.IAmenitiesFactory;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.discount_of_home.IDiscountOfHomeFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.item_favorites.IItemFavoritesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.factories.user_evaluate.IUserEvaluateFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.models.UserHomeDetailModel;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateInfo;
import com.hcmute.yourtours.models.user_evaluate.filter.EvaluateFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppHandleViewHomeFactory implements IAppHandleViewHomeFactory {
    private final IHomesFactory iHomesFactory;
    private final IBookHomeFactory iBookHomeFactory;
    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;
    private final IUserEvaluateFactory iUserEvaluateFactory;
    private final IItemFavoritesFactory iItemFavoritesFactory;
    private final IRoomsOfHomeFactory iRoomsOfHomeFactory;
    private final IOwnerOfHomeFactory iOwnerOfHomeFactory;
    private final IAmenitiesFactory iAmenitiesFactory;
    private final IBedsOfHomeFactory iBedsOfHomeFactory;
    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;
    private final IPriceOfHomeFactory iPriceOfHomeFactory;
    private final IDiscountOfHomeFactory iDiscountOfHomeFactory;

    public AppHandleViewHomeFactory(
            @Qualifier("appHomesFactory") IHomesFactory iHomesFactory,
            @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory,
            IGetUserFromTokenFactory iGetUserFromTokenFactory,
            IUserEvaluateFactory iUserEvaluateFactory,
            IItemFavoritesFactory iItemFavoritesFactory,
            IRoomsOfHomeFactory iRoomsOfHomeFactory,
            IOwnerOfHomeFactory iOwnerOfHomeFactory,
            @Qualifier("appAmenitiesFactory") IAmenitiesFactory iAmenitiesFactory,
            IBedsOfHomeFactory iBedsOfHomeFactory,
            ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
            IPriceOfHomeFactory iPriceOfHomeFactory,
            IDiscountOfHomeFactory iDiscountOfHomeFactory) {
        this.iHomesFactory = iHomesFactory;
        this.iBookHomeFactory = iBookHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
        this.iUserEvaluateFactory = iUserEvaluateFactory;
        this.iItemFavoritesFactory = iItemFavoritesFactory;
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
        this.iAmenitiesFactory = iAmenitiesFactory;
        this.iBedsOfHomeFactory = iBedsOfHomeFactory;
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iPriceOfHomeFactory = iPriceOfHomeFactory;
        this.iDiscountOfHomeFactory = iDiscountOfHomeFactory;
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

            PriceOfHomeResponse price = iPriceOfHomeFactory.getCostBetweenDay(GetPriceOfHomeRequest.builder()
                    .dateTo(LocalDate.now())
                    .dateFrom(LocalDate.now())
                    .homeId(homeId)
                    .build());

            List<DiscountOfHomeViewModel> discounts = iDiscountOfHomeFactory.getDiscountsOfHomeView(homeId)
                    .stream().filter(item -> !(item.getConfig() == null || item.getConfig().getPercent() == null) ).collect(Collectors.toList());

            UserHomeDetailModel result = UserHomeDetailModel.builder()
                    .homeDetail(homeDetail)
                    .evaluates(evaluates)
                    .dateIsBooked(getDatesIdBooked(homeId))
                    .ownerName(iOwnerOfHomeFactory.getMainOwnerOfHome(homeId))
                    .rooms(iRoomsOfHomeFactory.getRoomHaveConfigBed(homeId))
                    .amenitiesView(iAmenitiesFactory.getAllByHomeId(homeId))
                    .descriptionHomeDetail(getDescriptionHomeDetail(homeDetail))
                    .surcharges(iSurchargeOfHomeFactory.getListSurchargeOfHome(homeId))
                    .totalCostBooking(price.getTotalCostWithSurcharge())
                    .discounts(discounts)
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
            e.printStackTrace();
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

    private String getDescriptionHomeDetail(HomeDetail detail) {
        StringBuilder builder = new StringBuilder();
        builder.append(detail.getNumberOfGuests());
        builder.append(" ");
        builder.append("Khách");
        builder.append(" . ");

        try {


            List<RoomOfHomeInfo> bedRooms = iRoomsOfHomeFactory.getAllByHomeIdAndCategoryId(detail.getId(), RoomCategoryIdConstant.BED_ROOM_CATEGORY_ID);
            if (!bedRooms.isEmpty()) {
                builder.append(bedRooms.size());
                builder.append(" ");
                builder.append("Phòng ngủ");
                builder.append(" . ");
            }

            Integer numberOfBed = iBedsOfHomeFactory.getNumberOfBedWithHomeId(detail.getId());
            builder.append(numberOfBed);
            builder.append(" ");
            builder.append("Giường");
            builder.append(" . ");

            List<RoomOfHomeInfo> bathRooms = iRoomsOfHomeFactory.getAllByHomeIdAndCategoryId(detail.getId(), RoomCategoryIdConstant.BATH_ROOM_CATEGORY_ID);
            if (!bedRooms.isEmpty()) {
                builder.append(bathRooms.size());
                builder.append(" ");
                builder.append("Phòng tắm");
                builder.append(" ");
            }
        } catch (InvalidException e) {
            // ignore
        }
        return builder.toString();
    }

}
