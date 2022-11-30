package com.hcmute.yourtours.factories.booking;

import com.hcmute.yourtours.commands.BookHomesCommand;
import com.hcmute.yourtours.constant.CornConstant;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.booking_guest_detail.IBookingGuestDetailFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        extends BasePersistDataFactory<UUID, BookHomeInfo, BookHomeDetail, Long, BookHomesCommand>
        implements IBookHomeFactory {

    protected final BookHomeRepository bookHomeRepository;
    protected final IHomesFactory iHomesFactory;
    protected final IUserFactory iUserFactory;
    protected final IBookingGuestDetailFactory iBookingGuestDetailFactory;

    protected BookHomeFactory(
            BookHomeRepository repository,
            @Qualifier("homesFactory") IHomesFactory iHomesFactory,
            IUserFactory iUserFactory,
            IBookingGuestDetailFactory iBookingGuestDetailFactory
    ) {
        super(repository);
        this.bookHomeRepository = repository;
        this.iHomesFactory = iHomesFactory;
        this.iUserFactory = iUserFactory;
        this.iBookingGuestDetailFactory = iBookingGuestDetailFactory;
    }

    @Override
    @NonNull
    protected Class<BookHomeDetail> getDetailClass() {
        return BookHomeDetail.class;
    }

    @Override
    public BookHomesCommand createConvertToEntity(BookHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return BookHomesCommand.builder()
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
                .build();

    }

    @Override
    public void updateConvertToEntity(BookHomesCommand entity, BookHomeDetail detail) throws InvalidException {
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
    }

    @Override
    public BookHomeDetail convertToDetail(BookHomesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
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
                .homeName(iHomesFactory.getDetailModel(entity.getHomeId(), null).getName())
                .customerName(iUserFactory.getDetailModel(entity.getUserId(), null).getFullName())
                .status(entity.getStatus())
                .totalCost(entity.getTotalCost())
                .id(entity.getBookId())
                .numberOfGuests(iBookingGuestDetailFactory.getNumberGuestsOfBooking(entity.getBookId()))
                .build();

    }

    @Override
    public BookHomeInfo convertToInfo(BookHomesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
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
                .homeName(iHomesFactory.getDetailModel(entity.getHomeId(), null).getName())
                .customerName(iUserFactory.getDetailModel(entity.getUserId(), null).getFullName())
                .totalCost(entity.getTotalCost())
                .id(entity.getBookId())
                .numberOfGuests(iBookingGuestDetailFactory.getNumberGuestsOfBooking(entity.getBookId()))
                .build();
    }


    @Override
    protected Long convertId(UUID id) throws InvalidException {
        return findByBookId(id).getId();
    }

    private BookHomesCommand findByBookId(UUID bookId) throws InvalidException {
        Optional<BookHomesCommand> optional = bookHomeRepository.findByBookId(bookId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BOOKING);
        }
        return optional.get();
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
                Optional<BookHomesCommand> optional = bookHomeRepository.findOneByBetweenDate(date, homeId);
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
            Optional<BookHomesCommand> optional = bookHomeRepository.findOneByBetweenDate(dateStart, homeId);
            if (optional.isPresent()) {
                throw new InvalidException(YourToursErrorCode.DATE_BOOKING_IS_INVALID);
            }
            dateStart = dateStart.plusDays(1);
        }
    }

    @Override
    public SuccessResponse handleCancelBooking(UUID bookingId) throws InvalidException {
        BookHomeDetail detail = getDetailModel(bookingId, null);
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
        updateModel(detail.getId(), detail);
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

        if (LocalDate.now().isAfter(detail.getDateEnd())) {
            throw new InvalidException(YourToursErrorCode.DATE_CHECK_IN_IS_EXCEED);
        }

        detail.setStatus(BookRoomStatusEnum.CHECK_IN);
        updateModel(detail.getId(), detail);
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
        return SuccessResponse.builder()
                .success(true)
                .build();
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
                List<BookHomesCommand> bookHomes = bookHomeRepository.findAllCommandNeedUpdateCheckOut(LocalDate.now(), BookRoomStatusEnum.CHECK_OUT.name());
                for (BookHomesCommand bookHome : bookHomes) {
                    if (bookHome.getStatus().equals(BookRoomStatusEnum.CANCELED)) {
                        bookHome.setStatus(BookRoomStatusEnum.CHECK_OUT);
                        repository.save(bookHome);
                    }
                }
                log.info("--- JOB SCHEDULE UPDATE STATUS BOOKING IS SUCCESSFUL !");
                return;
            } catch (Exception e) {
                log.error("--- JOB SCHEDULE UPDATE STATUS BOOKING IS ERROR !");
            }
        }
    }
}
