package com.hcmute.yourtours.factories.guests_of_home;

import com.hcmute.yourtours.commands.GuestsOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeDetail;
import com.hcmute.yourtours.models.guests_of_home.GuestOfHomeInfo;
import com.hcmute.yourtours.repositories.GuestsOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuestsOfHomeFactory
        extends BasePersistDataFactory<UUID, GuestOfHomeInfo, GuestOfHomeDetail, Long, GuestsOfHomeCommand>
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
    public GuestsOfHomeCommand createConvertToEntity(GuestOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return GuestsOfHomeCommand.builder()
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .amount(detail.getAmount())
                .build();
    }

    @Override
    public void updateConvertToEntity(GuestsOfHomeCommand entity, GuestOfHomeDetail detail) throws InvalidException {
        entity.setCategoryId(detail.getCategoryId());
        entity.setHomeId(detail.getHomeId());
        entity.setAmount(detail.getAmount());
    }

    @Override
    public GuestOfHomeDetail convertToDetail(GuestsOfHomeCommand entity) throws InvalidException {
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
    public GuestOfHomeInfo convertToInfo(GuestsOfHomeCommand entity) throws InvalidException {
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
        GuestsOfHomeCommand guest = guestsOfHomeRepository.findByGuestOfHomeId(id).orElse(null);
        if (guest == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_GUESTS_OF_HOME);
        }
        return guest.getId();
    }
}
