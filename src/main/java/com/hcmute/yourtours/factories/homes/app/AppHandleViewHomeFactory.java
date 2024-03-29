package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.constant.RoomCategoryIdConstant;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.amenities.IAmenitiesFactory;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.discount_of_home.IDiscountOfHomeFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.models.discount_of_home.models.DiscountOfHomeViewModel;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.models.UserHomeDetailModel;
import com.hcmute.yourtours.models.homes.projections.CalculateAverageRateProjection;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppHandleViewHomeFactory implements IAppHandleViewHomeFactory {
    private final IHomesFactory iHomesFactory;
    private final IBookHomeFactory iBookHomeFactory;
    private final IRoomsOfHomeFactory iRoomsOfHomeFactory;
    private final IOwnerOfHomeFactory iOwnerOfHomeFactory;
    private final IAmenitiesFactory iAmenitiesFactory;
    private final IBedsOfHomeFactory iBedsOfHomeFactory;
    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;
    private final IPriceOfHomeFactory iPriceOfHomeFactory;
    private final IDiscountOfHomeFactory iDiscountOfHomeFactory;
    private final HomesRepository homesRepository;

    public AppHandleViewHomeFactory(
            @Qualifier("appHomesFactory") IHomesFactory iHomesFactory,
            @Qualifier("bookHomeFactory") IBookHomeFactory iBookHomeFactory,
            IRoomsOfHomeFactory iRoomsOfHomeFactory,
            IOwnerOfHomeFactory iOwnerOfHomeFactory,
            @Qualifier("appAmenitiesFactory") IAmenitiesFactory iAmenitiesFactory,
            IBedsOfHomeFactory iBedsOfHomeFactory,
            ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
            IPriceOfHomeFactory iPriceOfHomeFactory,
            IDiscountOfHomeFactory iDiscountOfHomeFactory,
            HomesRepository homesRepository
    ) {
        this.iHomesFactory = iHomesFactory;
        this.iBookHomeFactory = iBookHomeFactory;
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
        this.iAmenitiesFactory = iAmenitiesFactory;
        this.iBedsOfHomeFactory = iBedsOfHomeFactory;
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iPriceOfHomeFactory = iPriceOfHomeFactory;
        this.iDiscountOfHomeFactory = iDiscountOfHomeFactory;
        this.homesRepository = homesRepository;
    }

    @Override
    public UserHomeDetailModel getDetailByHomeId(UUID homeId) throws InvalidException {

        try {
            HomeDetail homeDetail = iHomesFactory.getDetailModel(homeId, null);

            PriceOfHomeResponse price = iPriceOfHomeFactory.getCostBetweenDay(GetPriceOfHomeRequest.builder()
                    .dateTo(LocalDate.now())
                    .dateFrom(LocalDate.now())
                    .homeId(homeId)
                    .build());

            List<DiscountOfHomeViewModel> discounts = iDiscountOfHomeFactory.getDiscountsOfHomeView(homeId)
                    .stream().filter(item -> !(item.getConfig() == null || item.getConfig().getPercent() == null)).collect(Collectors.toList());

            UserHomeDetailModel result = UserHomeDetailModel.builder()
                    .homeDetail(homeDetail)
                    .dateIsBooked(getDatesIdBooked(homeId))
                    .ownerName(iOwnerOfHomeFactory.getMainOwnerOfHome(homeId))
                    .rooms(iRoomsOfHomeFactory.getRoomHaveConfigBed(homeId))
                    .amenitiesView(iAmenitiesFactory.getAllByHomeId(homeId))
                    .descriptionHomeDetail(getDescriptionHomeDetail(homeDetail))
                    .surcharges(iSurchargeOfHomeFactory.getListSurchargeOfHome(homeId))
                    .totalCostBooking(price.getTotalCostWithSurcharge())
                    .discounts(discounts)
                    .build();


            return calculateAverageRate(result);

        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidException(YourToursErrorCode.CONVERT_TO_UUID_IS_ERROR);
        }

    }

    private UserHomeDetailModel calculateAverageRate(UserHomeDetailModel result) {

        CalculateAverageRateProjection projection = homesRepository.calculateAverageRate(result.getHomeDetail().getId());

        result.getHomeDetail().setAverageRate(projection.getAverageRate());
        result.getHomeDetail().setNumberOfReviews(projection.getNumberOfReview());
        return result;

    }

    private List<String> getDatesIdBooked(UUID homeId) {
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

        List<LocalDate> dates = iBookHomeFactory.getDatesIsBooked(models, homeId);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<String> result = new ArrayList<>();
        for (LocalDate date : dates) {
            result.add(date.format(formatters));
        }
        return result;
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
