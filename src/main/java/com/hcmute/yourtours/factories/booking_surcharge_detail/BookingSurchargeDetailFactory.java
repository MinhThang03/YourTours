package com.hcmute.yourtours.factories.booking_surcharge_detail;

import com.hcmute.yourtours.entities.BookingHomeSurchargeDetailCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailInfo;
import com.hcmute.yourtours.repositories.BookingSurchargeDetailRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingSurchargeDetailFactory
        extends BasePersistDataFactory<UUID, BookingSurchargeDetailInfo, BookingSurchargeDetailDetail, Long, BookingHomeSurchargeDetailCommand>
        implements IBookingSurchargeDetailFactory {

    private final BookingSurchargeDetailRepository bookingSurchargeDetailRepository;

    protected BookingSurchargeDetailFactory(BookingSurchargeDetailRepository repository) {
        super(repository);
        this.bookingSurchargeDetailRepository = repository;
    }

    @Override
    @NonNull
    protected Class<BookingSurchargeDetailDetail> getDetailClass() {
        return BookingSurchargeDetailDetail.class;
    }

    @Override
    public BookingHomeSurchargeDetailCommand createConvertToEntity(BookingSurchargeDetailDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }

        return BookingHomeSurchargeDetailCommand.builder()
                .booking(detail.getBooking())
                .costOfSurcharge(detail.getCostOfSurcharge())
                .surchargeId(detail.getSurchargeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(BookingHomeSurchargeDetailCommand entity, BookingSurchargeDetailDetail detail) throws InvalidException {
        entity.setBooking(detail.getBooking());
        entity.setCostOfSurcharge(detail.getCostOfSurcharge());
        entity.setSurchargeId(detail.getSurchargeId());
    }

    @Override
    public BookingSurchargeDetailDetail convertToDetail(BookingHomeSurchargeDetailCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BookingSurchargeDetailDetail.builder()
                .booking(entity.getBooking())
                .costOfSurcharge(entity.getCostOfSurcharge())
                .surchargeId(entity.getSurchargeId())
                .id(entity.getBookingSurchargeDetailId())
                .build();
    }

    @Override
    public BookingSurchargeDetailInfo convertToInfo(BookingHomeSurchargeDetailCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BookingSurchargeDetailInfo.builder()
                .booking(entity.getBooking())
                .costOfSurcharge(entity.getCostOfSurcharge())
                .surchargeId(entity.getSurchargeId())
                .id(entity.getBookingSurchargeDetailId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<BookingHomeSurchargeDetailCommand> optional = bookingSurchargeDetailRepository.findByBookingSurchargeDetailId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BOOKING_SURCHARGE_DETAIL);
        }
        return optional.get().getId();
    }

    @Override
    public void createListModel(UUID bookingId, List<BookingSurchargeDetailDetail> listDetail) throws InvalidException {
        if (listDetail == null) {
            return;
        }

        List<BookingHomeSurchargeDetailCommand> listDelete = bookingSurchargeDetailRepository.findAllByBooking(bookingId);
        for (BookingHomeSurchargeDetailCommand item : listDelete) {
            deleteModel(item.getBookingSurchargeDetailId(), null);
        }

        for (BookingSurchargeDetailDetail item : listDetail) {
            item.setBooking(bookingId);
            createModel(item);
        }
    }
}
