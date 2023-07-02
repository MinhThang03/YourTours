package com.hcmute.yourtours.factories.booking;

import com.hcmute.yourtours.constant.CornConstant;
import com.hcmute.yourtours.entities.BookHomes;
import com.hcmute.yourtours.enums.AdminChartTypeEnum;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.MonthEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.external_modules.email.IEmailFactory;
import com.hcmute.yourtours.factories.booking_guest_detail.IBookingGuestDetailFactory;
import com.hcmute.yourtours.factories.common.IGetUserFromTokenFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.websocket.IWebSocketFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.util.TimeUtil;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.projections.GetOwnerNameAndHomeNameProjection;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminHomeChartFilter;
import com.hcmute.yourtours.models.statistic.admin.filter.AdminStatisticDateFilter;
import com.hcmute.yourtours.models.statistic.admin.models.AdminChartStatistic;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminChartProjection;
import com.hcmute.yourtours.models.statistic.admin.projections.AdminStatisticHomeProjection;
import com.hcmute.yourtours.models.statistic.common.RevenueStatistic;
import com.hcmute.yourtours.models.statistic.host.models.HomeBookingStatistic;
import com.hcmute.yourtours.models.statistic.host.projections.HomeBookingStatisticProjection;
import com.hcmute.yourtours.models.statistic.host.projections.OwnerHomeStatisticProjection;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookHomeFactory
        extends BasePersistDataFactory<UUID, BookHomeInfo, BookHomeDetail, UUID, BookHomes>
        implements IBookHomeFactory {

    protected final BookHomeRepository bookHomeRepository;
    protected final IHomesFactory iHomesFactory;
    protected final IUserFactory iUserFactory;
    protected final IBookingGuestDetailFactory iBookingGuestDetailFactory;
    protected final IOwnerOfHomeFactory iOwnerOfHomeFactory;
    protected final IGetUserFromTokenFactory iGetUserFromTokenFactory;
    protected final IEmailFactory iEmailFactory;
    protected final ApplicationEventPublisher applicationEventPublisher;
    protected final IWebSocketFactory iWebSocketFactory;

    protected BookHomeFactory
            (
                    BookHomeRepository repository,
                    @Qualifier("homesFactory") IHomesFactory iHomesFactory,
                    IUserFactory iUserFactory,
                    IBookingGuestDetailFactory iBookingGuestDetailFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory,
                    IGetUserFromTokenFactory iGetUserFromTokenFactory,
                    IEmailFactory iEmailFactory,
                    ApplicationEventPublisher applicationEventPublisher,
                    IWebSocketFactory iWebSocketFactory
            ) {
        super(repository);
        this.bookHomeRepository = repository;
        this.iHomesFactory = iHomesFactory;
        this.iUserFactory = iUserFactory;
        this.iBookingGuestDetailFactory = iBookingGuestDetailFactory;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
        this.iEmailFactory = iEmailFactory;
        this.applicationEventPublisher = applicationEventPublisher;
        this.iWebSocketFactory = iWebSocketFactory;
    }

    @Override
    @NonNull
    protected Class<BookHomeDetail> getDetailClass() {
        return BookHomeDetail.class;
    }

    @Override
    public BookHomes createConvertToEntity(BookHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return BookHomes.builder()
                .dateStart(detail.getDateStart())
                .dateEnd(detail.getDateEnd())
                .phoneNumber(detail.getPhoneNumber())
                .email(detail.getEmail())
                .cost(detail.getCost())
                .paymentMethod(detail.getPaymentMethod())
                .visaAccount(detail.getVisaAccount())
                .homeId(detail.getHomeId())
                .userId(detail.getUserId())
                .status(BookRoomStatusEnum.WAITING)
                .totalCost(detail.getTotalCost())
                .percent(detail.getPercent())
                .moneyPayed(detail.getMoneyPayed())
                .costOfHost(detail.getCostOfHost())
                .costOfAdmin(detail.getCostOfAdmin())
                .comment(detail.getComment())
                .rates(detail.getRates())
                .build();

    }

    @Override
    public void updateConvertToEntity(BookHomes entity, BookHomeDetail detail) throws InvalidException {
        entity.setDateStart(detail.getDateStart());
        entity.setDateEnd(detail.getDateEnd());
        entity.setPhoneNumber(detail.getPhoneNumber());
        entity.setEmail(detail.getEmail());
        entity.setCost(detail.getCost());
        entity.setPaymentMethod(detail.getPaymentMethod());
        entity.setVisaAccount(detail.getVisaAccount());
        entity.setHomeId(detail.getHomeId());
        entity.setUserId(detail.getUserId());
        entity.setStatus(detail.getStatus());
        entity.setTotalCost(detail.getTotalCost());
        entity.setPercent(detail.getPercent());
        entity.setMoneyPayed(detail.getMoneyPayed());
        entity.setComment(detail.getComment());
        entity.setRates(detail.getRates());
    }

    @Override
    public BookHomeDetail convertToDetail(BookHomes entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        HomeDetail homeDetail = iHomesFactory.getDetailModel(entity.getHomeId(), null);

        return BookHomeDetail.builder()
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .cost(entity.getCost())
                .paymentMethod(entity.getPaymentMethod())
                .visaAccount(entity.getVisaAccount())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .homeName(homeDetail.getName())
                .customerName(iUserFactory.getDetailModel(entity.getUserId(), null).getFullName())
                .status(entity.getStatus())
                .totalCost(entity.getTotalCost())
                .id(entity.getId())
                .thumbnail(homeDetail.getThumbnail())
                .owner(iOwnerOfHomeFactory.getMainOwnerOfHome(entity.getHomeId()))
                .numberOfGuests(iBookingGuestDetailFactory.getNumberGuestsOfBooking(entity.getId()))
                .homeAddressDetail(homeDetail.getAddressDetail())
                .homeProvinceCode(homeDetail.getProvinceCode())
                .percent(entity.getPercent())
                .costOfHost(entity.getCostOfHost())
                .costOfAdmin(entity.getCostOfAdmin())
                .moneyPayed(entity.getMoneyPayed())
                .refundPolicy(homeDetail.getRefundPolicy())
                .comment(entity.getComment())
                .rates(entity.getRates())
                .build();

    }

    @Override
    public BookHomeInfo convertToInfo(BookHomes entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        HomeDetail homeDetail = iHomesFactory.getDetailModel(entity.getHomeId(), null);

        return BookHomeInfo.builder()
                .dateStart(entity.getDateStart())
                .dateEnd(entity.getDateEnd())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .cost(entity.getCost())
                .paymentMethod(entity.getPaymentMethod())
                .visaAccount(entity.getVisaAccount())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .homeName(homeDetail.getName())
                .thumbnail(homeDetail.getThumbnail())
                .owner(iOwnerOfHomeFactory.getMainOwnerOfHome(entity.getHomeId()))
                .customerName(iUserFactory.getDetailModel(entity.getUserId(), null).getFullName())
                .totalCost(entity.getTotalCost())
                .id(entity.getId())
                .numberOfGuests(iBookingGuestDetailFactory.getNumberGuestsOfBooking(entity.getId()))
                .homeAddressDetail(homeDetail.getAddressDetail())
                .homeProvinceCode(homeDetail.getProvinceCode())
                .percent(entity.getPercent())
                .homeProvinceName(homeDetail.getProvinceName())
                .costOfHost(entity.getCostOfHost())
                .costOfAdmin(entity.getCostOfAdmin())
                .moneyPayed(entity.getMoneyPayed())
                .refundPolicy(homeDetail.getRefundPolicy())
                .build();
    }


    @Override
    public boolean existByUserIdAndHomeId(UUID userId, UUID homeId) {
        return bookHomeRepository.existsByUserIdAndHomeId(userId, homeId);
    }

    @Override
    public List<LocalDate> getDatesIsBooked(List<MonthAndYearModel> months, UUID homeId) {
        List<LocalDate> result = new ArrayList<>();
        for (MonthAndYearModel month : months) {
            List<LocalDate> dates = getListDayOfMonth(month.getMonth(), month.getYear());
            for (LocalDate date : dates) {
                Optional<BookHomes> optional = bookHomeRepository.findOneByBetweenDate(date, homeId);
                if (optional.isPresent()) {
                    result.add(date);
                }
            }
        }
        return result;
    }

    @Override
    public void checkDateBookingOfHomeValid(LocalDate dateStart, LocalDate dateEnd, UUID homeId) throws InvalidException {
        while (!dateStart.isAfter(dateEnd)) {
            Optional<BookHomes> optional = bookHomeRepository.findOneByBetweenDate(dateStart, homeId);
            if (optional.isPresent()) {
                throw new InvalidException(YourToursErrorCode.DATE_BOOKING_IS_INVALID);
            }
            dateStart = dateStart.plusDays(1);
        }
    }

    @Override
    public SuccessResponse handleCancelBooking(UUID bookingId) throws InvalidException {

        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();

        BookHomeDetail detail = getDetailModel(bookingId, null);

        if (!userId.equals(detail.getUserId())) {
            throw new InvalidException(YourToursErrorCode.NO_PERMISSION_CANCEL_BOOKING);
        }

        HomeDetail homeDetail = iHomesFactory.getDetailModel(detail.getHomeId(), null);
        RefundPolicyEnum refund = homeDetail.getRefundPolicy();

        int numberOfDate = 0;

        switch (refund) {
            case NO_REFUND:
                throw new InvalidException(YourToursErrorCode.BOOKING_HOME_IS_NOT_REFUND);
            case BEFORE_ONE_DAY:
                numberOfDate = 1;
                break;
            case BEFORE_SEVEN_DAYS:
                numberOfDate = 7;
                break;
        }

        LocalDate dateCompare = detail.getDateStart().minusDays(numberOfDate);
        if (LocalDate.now().isAfter(dateCompare)) {
            throw new InvalidException(YourToursErrorCode.DATE_CANCELED_IS_EXCEED);
        }

        detail.setStatus(BookRoomStatusEnum.CANCELED);
        detail = updateModel(detail.getId(), detail);

        GetOwnerNameAndHomeNameProjection projection = iHomesFactory.getOwnerNameAndHomeNameProjection(detail.getHomeId());
        detail.setOwner(projection.getOwnerName());
        detail.setBaseCost(projection.getBaseCost());
        detail.setHomeName(projection.getHomeName());
        detail.setLastModifiedDate(TimeUtil.toStringDate(LocalDateTime.now()));

        iWebSocketFactory.sendCancelMessage(projection.getOwnerId(), detail.getHomeId());

        applicationEventPublisher.publishEvent(detail);
        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public SuccessResponse handleCheckInBooking(UUID bookingId) throws InvalidException {
        BookHomeDetail detail = getDetailModel(bookingId, null);

        if (!detail.getStatus().equals(BookRoomStatusEnum.WAITING)) {
            throw new InvalidException(YourToursErrorCode.BOOK_HOME_CHECK_IN_IS_IN_VALID);
        }

        if (LocalDate.now().isBefore(detail.getDateStart())) {
            throw new InvalidException(YourToursErrorCode.DATE_CHECK_IN_IS_BEFORE);
        }

        if (LocalDate.now().isAfter(detail.getDateEnd())) {
            throw new InvalidException(YourToursErrorCode.DATE_CHECK_IN_IS_EXCEED);
        }

        detail.setStatus(BookRoomStatusEnum.CHECK_IN);
        detail.setMoneyPayed(detail.getTotalCost());
        updateModel(detail.getId(), detail);

        iWebSocketFactory.sendCheckInMessage(detail.getUserId(), detail.getHomeId());

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public SuccessResponse handleCheckOutBooking(UUID bookingId) throws InvalidException {
        BookHomeDetail detail = getDetailModel(bookingId, null);

        if (!detail.getStatus().equals(BookRoomStatusEnum.CHECK_IN)) {
            throw new InvalidException(YourToursErrorCode.BOOK_HOME_CHECK_OUT_IS_IN_VALID);
        }

        detail.setStatus(BookRoomStatusEnum.CHECK_OUT);
        updateModel(detail.getId(), detail);

        iWebSocketFactory.sendCheckOutMessage(detail.getUserId(), detail.getHomeId());

        return SuccessResponse.builder()
                .success(true)
                .build();
    }

    @Override
    public Long totalBookingOfOwner(UUID ownerId, Integer year) {
        return bookHomeRepository.countTotalBookingOfOwner(ownerId, year);
    }

    @Override
    public Long totalBookingOfOwnerFinish(UUID ownerId, Integer year) {
        return bookHomeRepository.countTotalBookingOfOwnerFinish(ownerId, BookRoomStatusEnum.CHECK_OUT.name(), year);
    }


    @Override
    public List<HomeBookingStatistic> getHomeBookingStatisticWithOwner(UUID ownerId, Integer year) {
        List<HomeBookingStatisticProjection> projections = bookHomeRepository.getHomeBookingStatisticWithOwner(ownerId, year);
        return projections.stream().map(
                item -> HomeBookingStatistic.builder()
                        .numberOfBooking(item.getNumberOfBooking())
                        .homeId(item.getHomeId())
                        .homeName(item.getHomeName())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<RevenueStatistic> getRevenueStatisticWithOwnerAndYear(UUID ownerId, Integer year) {
        List<RevenueStatistic> result = new ArrayList<>();

        for (MonthEnum item : MonthEnum.values()) {
            Double revenue = bookHomeRepository.getRevenueWithOwnerIdAndYear(ownerId, BookRoomStatusEnum.CHECK_OUT.name(), item.getMonthValue(), year);
            result.add(RevenueStatistic.builder()
                    .amount(revenue)
                    .month(item.getMonthName())
                    .build());
        }
        return result;
    }


    @Override
    public AdminChartStatistic getAdminChart(AdminHomeChartFilter filter) {
        if (filter.getType().equals(AdminChartTypeEnum.BOOKING)) {
            return AdminChartStatistic.builder()
                    .revenueStatistics(getRevenueBookingStatisticWithAdminAndYear(filter.getYear()))
                    .build();
        }

        return AdminChartStatistic.builder()
                .revenueStatistics(getRevenueStatisticWithAdminAndYear(filter.getYear()))
                .build();
    }


    private List<RevenueStatistic> getRevenueStatisticWithAdminAndYear(Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        List<RevenueStatistic> result = new ArrayList<>();
        List<AdminChartProjection> revenue = bookHomeRepository.getRevenueRevenueWithAdminIdAndYear(BookRoomStatusEnum.CHECK_OUT.name(), year);

        for (MonthEnum item : MonthEnum.values()) {
            result.add(RevenueStatistic.builder()
                    .amount(revenue.get(item.getMonthValue() - 1).getValue())
                    .month(item.getMonthName())
                    .build());
        }
        return result;
    }

    private List<RevenueStatistic> getRevenueBookingStatisticWithAdminAndYear(Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        List<RevenueStatistic> result = new ArrayList<>();
        List<AdminChartProjection> revenue = bookHomeRepository.getRevenueBookingWithAdminIdAndYear(year);

        for (MonthEnum item : MonthEnum.values()) {
            result.add(RevenueStatistic.builder()
                    .amount(revenue.get(item.getMonthValue() - 1).getValue())
                    .month(item.getMonthName())
                    .build());
        }
        return result;
    }


    @Override
    protected void preCreate(BookHomeDetail detail) throws InvalidException {
        iHomesFactory.checkExistsByHomeId(detail.getHomeId());
        if (detail.getUserId() != null) {
            iUserFactory.checkExistsByUserId(detail.getUserId());
        }

        if (detail.getDateStart().isBefore(LocalDate.now())) {
            throw new InvalidException(YourToursErrorCode.DATE_START_BOOKING_IN_VALID);
        }

        if (detail.getDateStart().isAfter(detail.getDateEnd())) {
            throw new InvalidException(YourToursErrorCode.DATE_BOOKING_IN_VALID);
        }
    }

    private List<LocalDate> getListDayOfMonth(Integer month, Integer year) {
        YearMonth ym = YearMonth.of(year, Month.of(month));
        LocalDate firstOfMonth = ym.atDay(1);
        LocalDate firstOfFollowingMonth = ym.plusMonths(1).atDay(1);
        return firstOfMonth.datesUntil(firstOfFollowingMonth).collect(Collectors.toList());
    }


    @Scheduled(cron = CornConstant.CORN_DAILY)
    protected void autoUpdateCheckOut() {
        for (int i = 0; i <= 3; i++) {
            try {
                handleAutoUpdateCheckOut();
                log.info("--- JOB SCHEDULE UPDATE STATUS BOOKING IS SUCCESSFUL !");
                return;
            } catch (Exception e) {
                log.error("--- JOB SCHEDULE UPDATE STATUS BOOKING IS ERROR !");
            }
        }
    }

    protected void handleAutoUpdateCheckOut() {
        List<BookHomes> bookHomes = bookHomeRepository.findAllCommandNeedUpdateCheckOut(LocalDate.now(), BookRoomStatusEnum.WAITING.name());
        for (BookHomes bookHome : bookHomes) {
            bookHome.setStatus(BookRoomStatusEnum.CHECK_OUT);
            repository.save(bookHome);
        }
    }


    @Override
    public Page<OwnerHomeStatisticProjection> getStatisticMonthForOwner(UUID userId, Integer month, Integer year, Pageable pageable) {
        return bookHomeRepository.ownerHomeStatistic(userId, month, year, pageable);
    }

    @Override
    public Page<AdminStatisticHomeProjection> getAdminStatisticHome(AdminStatisticDateFilter filter, Pageable pageable) {
        return bookHomeRepository.adminStatisticHome(filter.getDateStart(), filter.getDateEnd(), pageable);
    }

}
