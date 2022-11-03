package com.hcmute.yourtours.factories.booking;

import com.hcmute.yourtours.commands.BookHomesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.booking.BookHomeDetail;
import com.hcmute.yourtours.models.booking.BookHomeInfo;
import com.hcmute.yourtours.repositories.BookHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookHomeFactory
        extends BasePersistDataFactory<UUID, BookHomeInfo, BookHomeDetail, Long, BookHomesCommand>
        implements IBookHomeFactory {

    private final BookHomeRepository bookHomeRepository;

    protected BookHomeFactory(BookHomeRepository repository) {
        super(repository);
        this.bookHomeRepository = repository;
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
}
