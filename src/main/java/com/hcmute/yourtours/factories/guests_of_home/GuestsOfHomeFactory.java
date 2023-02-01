package com.hcmute.yourtours.factories.guests_of_home;

import com.hcmute.yourtours.entities.GuestsOfHome;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeDetail;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeInfo;
import com.hcmute.yourtours.repositories.GuestsOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GuestsOfHomeFactory
        extends BasePersistDataFactory<UUID, GuestOfHomeInfo, GuestOfHomeDetail, Long, GuestsOfHome>
        implements IGuestsOfHomeFactory {

    private final GuestsOfHomeRepository guestsOfHomeRepository;

    protected GuestsOfHomeFactory(GuestsOfHomeRepository repository) {
        super(repository);
        this.guestsOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<GuestOfHomeDetail> getDetailClass() {
        return GuestOfHomeDetail.class;
    }

    @Override
    public GuestsOfHome createConvertToEntity(GuestOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return GuestsOfHome.builder()
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .amount(detail.getAmount())
                .build();
    }

    @Override
    public void updateConvertToEntity(GuestsOfHome entity, GuestOfHomeDetail detail) throws InvalidException {
        entity.setCategoryId(detail.getCategoryId());
        entity.setHomeId(detail.getHomeId());
        entity.setAmount(detail.getAmount());
    }

    @Override
    public GuestOfHomeDetail convertToDetail(GuestsOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return GuestOfHomeDetail.builder()
                .id(entity.getGuestOfHomeId())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public GuestOfHomeInfo convertToInfo(GuestsOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return GuestOfHomeInfo.builder()
                .id(entity.getGuestOfHomeId())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        GuestsOfHome guest = guestsOfHomeRepository.findByGuestOfHomeId(id).orElse(null);
        if (guest == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_GUESTS_OF_HOME);
        }
        return guest.getId();
    }
}
