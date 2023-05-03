package com.hcmute.yourtours.factories.booking.app;

import com.hcmute.yourtours.constant.FeeRateOfAdminConstant;
import com.hcmute.yourtours.constant.SubjectEmailConstant;
import com.hcmute.yourtours.entities.BookHomes;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.external_modules.email.IEmailFactory;
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
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.libs.util.constant.TimePattern;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.filter.AppBookingFilter;
import com.hcmute.yourtours.models.booking.filter.BookingEvaluateFilter;
import com.hcmute.yourtours.models.booking.projections.GetDetailBookingProjection;
import com.hcmute.yourtours.models.booking.projections.GetPageEvaluateProjection;
import com.hcmute.yourtours.models.booking.request.CreateCommentRequest;
import com.hcmute.yourtours.models.booking.response.GetPageEvaluateResponse;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
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
            detail.setCustomerName(userDetail.getFullName());

            if (userDetail.getStatus() == null || !userDetail.getStatus().equals(UserStatusEnum.ACTIVE)) {
                throw new InvalidException(YourToursErrorCode.ACCOUNT_NOT_ACTIVE);
            }
        }

        // Cập nhật số lượng đặt của ngôi nhà
        HomeDetail homeDetail = iHomesFactory.getDetailModel(detail.getHomeId(), null);
        homeDetail.setNumberOfBooking(homeDetail.getNumberOfBooking() + 1);
        iHomesFactory.updateModel(homeDetail.getId(), homeDetail);

        //Kiểm tra thông tin ngày đặt
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
    protected void postCreate(BookHomes entity, BookHomeDetail detail) throws InvalidException {
        iBookingGuestDetailFactory.createListModel(entity.getId(), detail.getGuests());
        iBookingSurchargeDetailFactory.createListModel(entity.getId(), detail.getSurcharges());

        GetOwnerNameAndHomeNameProjection projection = iHomesFactory.getOwnerNameAndHomeNameProjection(entity.getHomeId());
        detail.setId(entity.getId());
        detail.setHomeName(projection.getHomeName());
        detail.setOwner(projection.getOwnerName());
        detail.setBaseCost(projection.getBaseCost());
        detail.setCreatedDate(TimeUtil.toStringDate(entity.getCreatedDate()));
        applicationEventPublisher.publishEvent(detail);
    }

    @Override
    protected <F extends BaseFilter> Page<BookHomes> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        UUID customerId = iGetUserFromTokenFactory.checkUnAuthorization();

        if (filter instanceof AppBookingFilter) {
            AppBookingFilter appBookingFilter = (AppBookingFilter) filter;
            return bookHomeRepository.getAppBookingPage(appBookingFilter.getStatus(), customerId, PageRequest.of(number, size));
        }

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

    @Override
    public SuccessResponse checkBooking(BookHomeDetail detail) throws InvalidException {
        if (detail.getEmail() == null || detail.getPhoneNumber() == null) {
            UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
            UserDetail userDetail = iUserFactory.getDetailModel(userId, null);

            if (userDetail.getStatus() == null || !userDetail.getStatus().equals(UserStatusEnum.ACTIVE)) {
                throw new InvalidException(YourToursErrorCode.ACCOUNT_NOT_ACTIVE);
            }
        }


        if (detail.getDateStart().isBefore(LocalDate.now())) {
            throw new InvalidException(YourToursErrorCode.DATE_START_BOOKING_IN_VALID);
        }

        if (detail.getDateStart().isAfter(detail.getDateEnd())) {
            throw new InvalidException(YourToursErrorCode.DATE_BOOKING_IN_VALID);
        }

        iHomesFactory.checkExistsByHomeId(detail.getHomeId());
        iHomesFactory.checkNumberOfGuestOfHome(detail.getHomeId(), detail.getGuests());

        super.checkDateBookingOfHomeValid(detail.getDateStart(), detail.getDateEnd(), detail.getHomeId());

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public BookHomeDetail createComment(CreateCommentRequest request) throws InvalidException {

        BookHomeDetail detail = getDetailModel(request.getBookId(), null);

        detail.setComment(request.getComment());
        detail.setRates(request.getRates());

        return updateModel(detail.getId(), detail);
    }

    @Override
    public BasePagingResponse<GetPageEvaluateResponse> getPageEvaluates(BookingEvaluateFilter filter, Integer number, Integer size) {


        Page<GetPageEvaluateProjection> projections = bookHomeRepository.getPageEvaluate(filter.getHomeId(), PageRequest.of(number, size));

        List<GetPageEvaluateResponse> responseList = new ArrayList<>();

        projections.getContent().forEach(item -> responseList.add(GetPageEvaluateResponse.builder()
                .bookingId(item.getBookingId())
                .userId(item.getUserId())
                .homeId(item.getHomeId())
                .avatar(item.getAvatar())
                .fullName(item.getFullName())
                .rates(item.getRates())
                .comment(item.getComment())
                .lateModifiedDate(TimeUtil.toStringDate(item.getLastModifiedDate(), TimePattern.dd_MM_yyyy_HH_mm_ss))
                .build()));

        return new BasePagingResponse<>(
                responseList,
                projections.getNumber(),
                projections.getSize(),
                projections.getTotalElements()
        );

    }

    @Override
    public BookHomeDetail customGetDetail(UUID bookingId) throws InvalidException {
        GetDetailBookingProjection projection = bookHomeRepository.getDetailBooking(bookingId);

        if (projection == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BOOKING);
        }

        return BookHomeDetail.builder()
                .homeName(projection.getHomeName())
                .baseCost(projection.getCostPerNight())
                .homeProvinceName(projection.getProvince())
                .totalCost(projection.getTotalCost())
                .moneyPayed(projection.getMoneyPayed())
                .dateStart(projection.getDateStart())
                .dateEnd(projection.getDateEnd())
                .createdDate(TimeUtil.toStringDate(projection.getCreatedDate(), TimePattern.dd_MM_yyyy_HH_mm_ss))
                .comment(projection.getComment())
                .rates(projection.getRates())
                .status(projection.getStatus())
                .userId(projection.getUserId())
                .homeId(projection.getHomeId())
                .customerName(projection.getUserName())
                .id(projection.getBookingId())
                .refundPolicy(projection.getRefundPolicy())
                .owner(projection.getOwnerName())
                .guests(iBookingGuestDetailFactory.getListByBookingId(projection.getBookingId()))
                .build();

    }

}
