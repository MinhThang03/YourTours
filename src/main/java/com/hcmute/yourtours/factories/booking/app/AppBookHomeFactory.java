package com.hcmute.yourtours.factories.booking.app;

import com.hcmute.yourtours.commands.BookHomesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.booking.BookHomeFactory;
import com.hcmute.yourtours.factories.booking_guest_detail.IBookingGuestDetailFactory;
import com.hcmute.yourtours.factories.booking_surcharge_detail.IBookingSurchargeDetailFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppBookHomeFactory extends BookHomeFactory implements IAppBookHomeFactory {

    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;
    private final IPriceOfHomeFactory iPriceOfHomeFactory;
    private final IBookingSurchargeDetailFactory iBookingSurchargeDetailFactory;

    protected AppBookHomeFactory
            (
                    BookHomeRepository repository,
                    @Qualifier("homesFactory") IHomesFactory iHomesFactory,
                    IUserFactory iUserFactory,
                    ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
                    IPriceOfHomeFactory iPriceOfHomeFactory,
                    IBookingSurchargeDetailFactory iBookingSurchargeDetailFactory,
                    IBookingGuestDetailFactory iBookingGuestDetailFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory
            ) {
        super(repository, iHomesFactory, iUserFactory, iBookingGuestDetailFactory, iOwnerOfHomeFactory, iGetUserFromTokenFactory);
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iPriceOfHomeFactory = iPriceOfHomeFactory;
        this.iBookingSurchargeDetailFactory = iBookingSurchargeDetailFactory;
    }

    @Override
    protected void preCreate(BookHomeDetail detail) throws InvalidException {

        iHomesFactory.checkExistsByHomeId(detail.getHomeId());

        if (detail.getDateStart().isBefore(LocalDate.now())) {
            throw new InvalidException(YourToursErrorCode.DATE_START_BOOKING_IN_VALID);
        }

        if (detail.getDateStart().isAfter(detail.getDateEnd())) {
            throw new InvalidException(YourToursErrorCode.DATE_BOOKING_IN_VALID);
        }

        super.checkDateBookingOfHomeValid(detail.getDateStart(), detail.getDateEnd(), detail.getHomeId());
        iHomesFactory.checkNumberOfGuestOfHome(detail.getHomeId(), detail.getGuests());

        double surchargeFee = 0.0;

        List<SurchargeHomeViewModel> surcharges = iSurchargeOfHomeFactory.getListSurchargeOfHome(detail.getHomeId());
        List<BookingSurchargeDetailDetail> bookingSurcharges = new ArrayList<>();
        for (SurchargeHomeViewModel item : surcharges) {
            bookingSurcharges.add(BookingSurchargeDetailDetail.builder()
                    .surchargeId(item.getSurchargeCategoryId())
                    .costOfSurcharge(item.getCost())
                    .build());
            surchargeFee += item.getCost();
        }

        detail.setSurcharges(bookingSurcharges);
        double cost = iPriceOfHomeFactory.getCostBetweenDay(GetPriceOfHomeRequest.builder()
                .homeId(detail.getHomeId())
                .dateFrom(detail.getDateStart())
                .dateTo(detail.getDateEnd())
                .build()).getTotalCost();

        detail.setCost(cost);
        detail.setTotalCost(cost + surchargeFee);

        if (detail.getEmail() == null || detail.getPhoneNumber() == null) {
            UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
            UserDetail userDetail = iUserFactory.getDetailModel(userId, null);
            detail.setEmail(userDetail.getEmail());
            detail.setUserId(userId);
            detail.setPhoneNumber(userDetail.getPhoneNumber());
        }
    }


    @Override
    protected void postCreate(BookHomesCommand entity, BookHomeDetail detail) throws InvalidException {
        iBookingGuestDetailFactory.createListModel(entity.getBookId(), detail.getGuests());
        iBookingSurchargeDetailFactory.createListModel(entity.getBookId(), detail.getSurcharges());
    }

    @Override
    protected <F extends BaseFilter> Page<BookHomesCommand> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        UUID customerId = iGetUserFromTokenFactory.checkUnAuthorization();

        return bookHomeRepository.findBookingOfUser(customerId, PageRequest.of(number, size));
    }
}
