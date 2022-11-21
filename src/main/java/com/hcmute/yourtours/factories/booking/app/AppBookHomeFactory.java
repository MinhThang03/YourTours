package com.hcmute.yourtours.factories.booking.app;

import com.hcmute.yourtours.commands.BookHomesCommand;
import com.hcmute.yourtours.factories.booking.BookHomeFactory;
import com.hcmute.yourtours.factories.booking_guest_detail.IBookingGuestDetailFactory;
import com.hcmute.yourtours.factories.booking_surcharge_detail.IBookingSurchargeDetailFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.price_of_home.IPriceOfHomeFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppBookHomeFactory extends BookHomeFactory implements IAppBookHomeFactory {

    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;
    private final IPriceOfHomeFactory iPriceOfHomeFactory;
    private final IBookingSurchargeDetailFactory iBookingSurchargeDetailFactory;
    private final IBookingGuestDetailFactory iBookingGuestDetailFactory;
    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;

    protected AppBookHomeFactory
            (
                    BookHomeRepository repository,
                    @Qualifier("homesFactory") IHomesFactory iHomesFactory,
                    IUserFactory iUserFactory,
                    ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
                    IPriceOfHomeFactory iPriceOfHomeFactory,
                    IBookingSurchargeDetailFactory iBookingSurchargeDetailFactory,
                    IBookingGuestDetailFactory iBookingGuestDetailFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory
            ) {
        super(repository, iHomesFactory, iUserFactory);
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iPriceOfHomeFactory = iPriceOfHomeFactory;
        this.iBookingSurchargeDetailFactory = iBookingSurchargeDetailFactory;
        this.iBookingGuestDetailFactory = iBookingGuestDetailFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
    }

    @Override
    protected void preCreate(BookHomeDetail detail) throws InvalidException {


        super.checkDateBookingOfHomeValid(detail.getDateStart(), detail.getDateEnd(), detail.getHomeId());

        double surchargeFee = 0.0;
        if (detail.getSurcharges() != null) {
            List<BookingSurchargeDetailDetail> arraySurcharge = new ArrayList<>();
            for (BookingSurchargeDetailDetail surcharge : detail.getSurcharges()) {
                SurchargeOfHomeDetail surchargeOfHomeDetail = iSurchargeOfHomeFactory.getByHomeIdAndCategoryId(detail.getHomeId(), surcharge.getSurchargeId());
                if (surchargeOfHomeDetail.getCost() == null) {
                    surcharge.setCostOfSurcharge(0.0);
                } else {
                    surcharge.setCostOfSurcharge(surchargeOfHomeDetail.getCost());
                }
                surchargeFee += surcharge.getCostOfSurcharge();
                arraySurcharge.add(surcharge);
            }

            detail.setSurcharges(arraySurcharge);
        }

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
            detail.setPhoneNumber(userDetail.getPhoneNumber());
        }
    }


    @Override
    protected void postCreate(BookHomesCommand entity, BookHomeDetail detail) throws InvalidException {
        iBookingGuestDetailFactory.createListModel(entity.getBookId(), detail.getGuests());
        iBookingSurchargeDetailFactory.createListModel(entity.getBookId(), detail.getSurcharges());
    }
}
