package com.hcmute.yourtours.factories.booking_surcharge_detail;

import com.hcmute.yourtours.entities.BookingSurchargeDetail;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailDetail;
import com.hcmute.yourtours.models.booking_surcharge_detail.BookingSurchargeDetailInfo;
import com.hcmute.yourtours.repositories.BookingSurchargeDetailRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingSurchargeDetailFactory
        extends BasePersistDataFactory<UUID, BookingSurchargeDetailInfo, BookingSurchargeDetailDetail, UUID, BookingSurchargeDetail>
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
    public BookingSurchargeDetail createConvertToEntity(BookingSurchargeDetailDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }

        return BookingSurchargeDetail.builder()
                .booking(detail.getBooking())
                .costOfSurcharge(detail.getCostOfSurcharge())
                .surchargeId(detail.getSurchargeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(BookingSurchargeDetail entity, BookingSurchargeDetailDetail detail) throws InvalidException {
        entity.setBooking(detail.getBooking());
        entity.setCostOfSurcharge(detail.getCostOfSurcharge());
        entity.setSurchargeId(detail.getSurchargeId());
    }

    @Override
    public BookingSurchargeDetailDetail convertToDetail(BookingSurchargeDetail entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BookingSurchargeDetailDetail.builder()
                .booking(entity.getBooking())
                .costOfSurcharge(entity.getCostOfSurcharge())
                .surchargeId(entity.getSurchargeId())
                .id(entity.getId())
                .build();
    }

    @Override
    public BookingSurchargeDetailInfo convertToInfo(BookingSurchargeDetail entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BookingSurchargeDetailInfo.builder()
                .booking(entity.getBooking())
                .costOfSurcharge(entity.getCostOfSurcharge())
                .surchargeId(entity.getSurchargeId())
                .id(entity.getId())
                .build();
    }


    @Override
    public void createListModel(UUID bookingId, List<BookingSurchargeDetailDetail> listDetail) throws InvalidException {
        if (listDetail == null) {
            return;
        }

        List<BookingSurchargeDetail> listDelete = bookingSurchargeDetailRepository.findAllByBooking(bookingId);
        for (BookingSurchargeDetail item : listDelete) {
            deleteModel(item.getId(), null);
        }

        for (BookingSurchargeDetailDetail item : listDetail) {
            item.setBooking(bookingId);
            createModel(item);
        }
    }
}
