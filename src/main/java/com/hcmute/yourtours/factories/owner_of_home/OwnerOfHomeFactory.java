package com.hcmute.yourtours.factories.owner_of_home;

import com.hcmute.yourtours.commands.OwnerOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeDetail;
import com.hcmute.yourtours.models.owner_of_home.OwnerOfHomeInfo;
import com.hcmute.yourtours.repositories.OwnerOfHomesRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerOfHomeFactory
        extends BasePersistDataFactory<UUID, OwnerOfHomeInfo, OwnerOfHomeDetail, Long, OwnerOfHomeCommand>
        implements IOwnerOfHomeFactory {

    private final OwnerOfHomesRepository ownerOfHomesRepository;

    protected OwnerOfHomeFactory(OwnerOfHomesRepository repository) {
        super(repository);
        this.ownerOfHomesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<OwnerOfHomeDetail> getDetailClass() {
        return OwnerOfHomeDetail.class;
    }

    @Override
    public OwnerOfHomeCommand createConvertToEntity(OwnerOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return OwnerOfHomeCommand.builder()
                .isMainOwner(detail.getIsMainOwner())
                .userId(detail.getUserId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(OwnerOfHomeCommand entity, OwnerOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setIsMainOwner(detail.getIsMainOwner());
        entity.setHomeId(detail.getHomeId());
    }

    @Override
    public OwnerOfHomeDetail convertToDetail(OwnerOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return OwnerOfHomeDetail.builder()
                .id(entity.getOwnerOfHomeId())
                .isMainOwner(entity.getIsMainOwner())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    public OwnerOfHomeInfo convertToInfo(OwnerOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return OwnerOfHomeInfo.builder()
                .id(entity.getOwnerOfHomeId())
                .isMainOwner(entity.getIsMainOwner())
                .homeId(entity.getHomeId())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        OwnerOfHomeCommand command = ownerOfHomesRepository.findByOwnerOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_OWNER_OF_HOME);
        }
        return command.getId();
    }
}
