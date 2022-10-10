package com.hcmute.yourtours.factories.rooms_of_home;

import com.hcmute.yourtours.commands.RoomsOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.RoomOfHomeInfo;
import com.hcmute.yourtours.repositories.RoomsOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomsOfHomeFactory
        extends BasePersistDataFactory<UUID, RoomOfHomeInfo, RoomOfHomeDetail, Long, RoomsOfHomeCommand>
        implements IRoomsOfHomeFactory {

    private final RoomsOfHomeRepository roomsOfHomeRepository;

    protected RoomsOfHomeFactory(RoomsOfHomeRepository repository) {
        super(repository);
        this.roomsOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<RoomOfHomeDetail> getDetailClass() {
        return RoomOfHomeDetail.class;
    }

    @Override
    public RoomsOfHomeCommand createConvertToEntity(RoomOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return RoomsOfHomeCommand.builder()
                .description(detail.getDescription())
                .homeId(detail.getHomeId())
                .categoryId(detail.getCategoryId())
                .build();
    }

    @Override
    public void updateConvertToEntity(RoomsOfHomeCommand entity, RoomOfHomeDetail detail) throws InvalidException {
        entity.setDescription(detail.getDescription());
        entity.setHomeId(detail.getHomeId());
        entity.setCategoryId(detail.getCategoryId());
    }

    @Override
    public RoomOfHomeDetail convertToDetail(RoomsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RoomOfHomeDetail.builder()
                .id(entity.getRoomOfHomeId())
                .description(entity.getDescription())
                .homeId(entity.getHomeId())
                .categoryId(entity.getCategoryId())
                .build();
    }

    @Override
    public RoomOfHomeInfo convertToInfo(RoomsOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RoomOfHomeInfo.builder()
                .id(entity.getRoomOfHomeId())
                .description(entity.getDescription())
                .homeId(entity.getHomeId())
                .categoryId(entity.getCategoryId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        RoomsOfHomeCommand room = roomsOfHomeRepository.findByRoomOfHomeId(id).orElse(null);
        if (room == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_ROOMS_OF_HOME);
        }
        return room.getId();
    }
}
