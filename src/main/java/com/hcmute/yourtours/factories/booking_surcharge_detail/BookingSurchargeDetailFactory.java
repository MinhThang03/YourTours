package com.hcmute.yourtours.factories.booking_surcharge_detail;

import com.hcmute.yourtours.entities.BookingHomeSurchargeDetail;
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
        extends BasePersistDataFactory<UUID, BookingSurchargeDetailInfo, BookingSurchargeDetailDetail, Long, BookingHomeSurchargeDetail>
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
    public BookingHomeSurchargeDetail createConvertToEntity(BookingSurchargeDetailDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }

        return BookingHomeSurchargeDetail.builder()
                .booking(detail.getBooking())
                .costOfSurcharge(detail.getCostOfSurcharge())
                .surchargeId(detail.getSurchargeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(BookingHomeSurchargeDetail entity, BookingSurchargeDetailDetail detail) throws InvalidException {
        entity.setBooking(detail.getBooking());
        entity.setCostOfSurcharge(detail.getCostOfSurcharge());
        entity.setSurchargeId(detail.getSurchargeId());
    }

    @Override
    public BookingSurchargeDetailDetail convertToDetail(BookingHomeSurchargeDetail entity) throws InvalidException {
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
    public BookingSurchargeDetailInfo convertToInfo(BookingHomeSurchargeDetail entity) throws InvalidException {
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
        Optional<BookingHomeSurchargeDetail> optional = bookingSurchargeDetailRepository.findByBookingSurchargeDetailId(id);
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

        List<BookingHomeSurchargeDetail> listDelete = bookingSurchargeDetailRepository.findAllByBooking(bookingId);
        for (BookingHomeSurchargeDetail item : listDelete) {
            deleteModel(item.getBookingSurchargeDetailId(), null);
        }

        for (BookingSurchargeDetailDetail item : listDetail) {
            item.setBooking(bookingId);
            createModel(item);
        }
    }
}
