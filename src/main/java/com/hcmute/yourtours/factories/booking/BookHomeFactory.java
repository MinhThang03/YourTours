package com.hcmute.yourtours.factories.booking;

import com.hcmute.yourtours.commands.BookHomesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.models.booking.models.MonthAndYearModel;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class BookHomeFactory
        extends BasePersistDataFactory<UUID, BookHomeInfo, BookHomeDetail, Long, BookHomesCommand>
        implements IBookHomeFactory {

    protected final BookHomeRepository bookHomeRepository;
    protected final IHomesFactory iHomesFactory;
    protected final IUserFactory iUserFactory;

    protected BookHomeFactory(BookHomeRepository repository,
                              @Qualifier("homesFactory") IHomesFactory iHomesFactory,
                              IUserFactory iUserFactory) {
        super(repository);
        this.bookHomeRepository = repository;
        this.iHomesFactory = iHomesFactory;
        this.iUserFactory = iUserFactory;
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
                .status(detail.getStatus())
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
    protected void preCreate(BookHomeDetail detail) throws InvalidException {
        iHomesFactory.checkExistsByHomeId(detail.getHomeId());
        if (detail.getUserId() != null) {
            iUserFactory.checkExistsByUserId(detail.getUserId());
        }
    }

    private List<LocalDate> getListDayOfMonth(Integer month, Integer year) {
        YearMonth ym = YearMonth.of(year, Month.of(month));
        LocalDate firstOfMonth = ym.atDay(1);
        LocalDate firstOfFollowingMonth = ym.plusMonths(1).atDay(1);
        return firstOfMonth.datesUntil(firstOfFollowingMonth).collect(Collectors.toList());
    }
}
