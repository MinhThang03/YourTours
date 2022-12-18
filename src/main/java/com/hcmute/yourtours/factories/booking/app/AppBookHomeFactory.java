package com.hcmute.yourtours.factories.booking.app;

import com.hcmute.yourtours.constant.FeeRateOfAdminConstant;
import com.hcmute.yourtours.constant.SubjectEmailConstant;
import com.hcmute.yourtours.email.IEmailFactory;
import com.hcmute.yourtours.entities.BookHomesCommand;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
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
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.homes.projections.GetOwnerNameAndHomeNameProjection;
import com.hcmute.yourtours.models.price_of_home.request.GetPriceOfHomeRequest;
import com.hcmute.yourtours.models.price_of_home.response.PriceOfHomeResponse;
import com.hcmute.yourtours.models.surcharges_of_home.models.SurchargeHomeViewModel;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IEmailFactory iEmailFactory,
                    ApplicationEventPublisher applicationEventPublisher
            ) {
        super
                (
                        repository,
                        iHomesFactory,
                        iUserFactory,
                        iBookingGuestDetailFactory,
                        iOwnerOfHomeFactory,
                        iGetUserFromTokenFactory,
                        iEmailFactory,
                        applicationEventPublisher
                );
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iPriceOfHomeFactory = iPriceOfHomeFactory;
        this.iBookingSurchargeDetailFactory = iBookingSurchargeDetailFactory;
    }

    @Override
    protected void preCreate(BookHomeDetail detail) throws InvalidException {

        if (detail.getEmail() == null || detail.getPhoneNumber() == null) {
            UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
            UserDetail userDetail = iUserFactory.getDetailModel(userId, null);
            detail.setEmail(userDetail.getEmail());
            detail.setUserId(userId);
            detail.setPhoneNumber(userDetail.getPhoneNumber());
            detail.setUserName(userDetail.getFullName());

            if (userDetail.getStatus() == null || !userDetail.getStatus().equals(UserStatusEnum.ACTIVE)) {
                throw new InvalidException(YourToursErrorCode.ACCOUNT_NOT_ACTIVE);
            }
        }

        iHomesFactory.checkExistsByHomeId(detail.getHomeId());

        if (detail.getDateStart().isBefore(LocalDate.now())) {
            throw new InvalidException(YourToursErrorCode.DATE_START_BOOKING_IN_VALID);
        }

        if (detail.getDateStart().isAfter(detail.getDateEnd())) {
            throw new InvalidException(YourToursErrorCode.DATE_BOOKING_IN_VALID);
        }

        super.checkDateBookingOfHomeValid(detail.getDateStart(), detail.getDateEnd(), detail.getHomeId());
        iHomesFactory.checkNumberOfGuestOfHome(detail.getHomeId(), detail.getGuests());


        List<SurchargeHomeViewModel> surcharges = iSurchargeOfHomeFactory.getListSurchargeOfHome(detail.getHomeId());
        List<BookingSurchargeDetailDetail> bookingSurcharges = new ArrayList<>();
        for (SurchargeHomeViewModel item : surcharges) {
            bookingSurcharges.add(BookingSurchargeDetailDetail.builder()
                    .surchargeId(item.getSurchargeCategoryId())
                    .costOfSurcharge(item.getCost())
                    .build());
        }

        detail.setSurcharges(bookingSurcharges);
        PriceOfHomeResponse priceOfHomeResponse = iPriceOfHomeFactory.getCostBetweenDay(GetPriceOfHomeRequest.builder()
                .homeId(detail.getHomeId())
                .dateFrom(detail.getDateStart())
                .dateTo(detail.getDateEnd())
                .build());

        detail.setCost(priceOfHomeResponse.getTotalCost());
        detail.setTotalCost(priceOfHomeResponse.getTotalCostWithSurcharge());
        detail.setPercent(detail.getPercent());

        double costOfAdmin = Math.round(detail.getTotalCost() * (FeeRateOfAdminConstant.FEE_RATE_OF_ADMIN / 100));
        detail.setCostOfAdmin(costOfAdmin);
        detail.setCostOfHost(detail.getTotalCost() - costOfAdmin);
        detail.setSurchargeCost(priceOfHomeResponse.getSurchargeCost());
    }


    @Override
    protected void postCreate(BookHomesCommand entity, BookHomeDetail detail) throws InvalidException {
        iBookingGuestDetailFactory.createListModel(entity.getBookId(), detail.getGuests());
        iBookingSurchargeDetailFactory.createListModel(entity.getBookId(), detail.getSurcharges());

        GetOwnerNameAndHomeNameProjection projection = iHomesFactory.getOwnerNameAndHomeNameProjection(entity.getHomeId());
        detail.setId(entity.getBookId());
        detail.setHomeName(projection.getHomeName());
        detail.setOwnerName(projection.getOwnerName());
        detail.setBaseCost(projection.getBaseCost());
        detail.setCreatedDate(TimeUtil.toStringDate(entity.getCreatedDate()));
        applicationEventPublisher.publishEvent(detail);
    }

    @Override
    protected <F extends BaseFilter> Page<BookHomesCommand> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        UUID customerId = iGetUserFromTokenFactory.checkUnAuthorization();

        return bookHomeRepository.findBookingOfUser(customerId, PageRequest.of(number, size));
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessageSocket(BookHomeDetail detail) {
        try {
            String emailContent;
            if (detail.getStatus().equals(BookRoomStatusEnum.WAITING)) {
                emailContent = iEmailFactory.getEmailSuccessBooking(detail);
                iEmailFactory.sendSimpleMessage(detail.getEmail(), SubjectEmailConstant.BOOKING_SUCCESS, emailContent);
            } else {
                emailContent = iEmailFactory.getEmailCancelBooking(detail);
                iEmailFactory.sendSimpleMessage(detail.getEmail(), SubjectEmailConstant.BOOKING_CANCEL, emailContent);
            }
        } catch (Exception ignored) {
            // ignore
        }
    }

}
