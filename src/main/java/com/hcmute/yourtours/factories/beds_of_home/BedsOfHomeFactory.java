package com.hcmute.yourtours.factories.beds_of_home;

import com.hcmute.yourtours.commands.BedsOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import com.hcmute.yourtours.models.beds_of_home.BedOfHomeInfo;
import com.hcmute.yourtours.repositories.BedsOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BedsOfHomeFactory
        extends BasePersistDataFactory<UUID, BedOfHomeInfo, BedOfHomeDetail, Long, BedsOfHomeCommand>
        implements IBedsOfHomeFactory {

    private final BedsOfHomeRepository bedsOfHomeRepository;

    protected BedsOfHomeFactory(BedsOfHomeRepository repository) {
        super(repository);
        this.bedsOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<BedOfHomeDetail> getDetailClass() {
        return BedOfHomeDetail.class;
    }

    @Override
    public BedsOfHomeCommand createConvertToEntity(BedOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }

        return BedsOfHomeCommand.builder()
                .categoryId(detail.getCategoryId())
                .roomOfHomeId(detail.getRoomOfHomeId())
                .amount(detail.getAmount())
                .build();
    }

    @Override
    public void updateConvertToEntity(BedsOfHomeCommand entity, BedOfHomeDetail detail) throws InvalidException {
        entity.setCategoryId(detail.getCategoryId());
        entity.setRoomOfHomeId(detail.getRoomOfHomeId());
        entity.setAmount(detail.getAmount());
    }

    @Override
    public BedOfHomeDetail convertToDetail(BedsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BedOfHomeDetail.builder()
                .id(entity.getBedOfHomeId())
                .categoryId(entity.getCategoryId())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    public BedOfHomeInfo convertToInfo(BedsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return BedOfHomeInfo.builder()
                .id(entity.getBedOfHomeId())
                .categoryId(entity.getCategoryId())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .amount(entity.getAmount())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        BedsOfHomeCommand bedConfig = bedsOfHomeRepository.findByBedOfHomeId(id).orElse(null);
        if (bedConfig == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_BEDS_OF_HOME);
        }
        return bedConfig.getId();
    }
}
