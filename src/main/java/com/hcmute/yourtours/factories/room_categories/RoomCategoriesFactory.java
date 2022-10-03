package com.hcmute.yourtours.factories.room_categories;

import com.hcmute.yourtours.commands.RoomCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoriesInfo;
import com.hcmute.yourtours.repositories.RoomCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RoomCategoriesFactory
        extends BasePersistDataFactory<UUID, RoomCategoriesInfo, RoomCategoriesDetail, Long, RoomCategoriesCommand>
        implements IRoomCategoriesFactory {

    private final RoomCategoriesRepository roomCategoriesRepository;

    protected RoomCategoriesFactory(RoomCategoriesRepository repository) {
        super(repository);
        this.roomCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<RoomCategoriesDetail> getDetailClass() {
        return RoomCategoriesDetail.class;
    }

    @Override
    public RoomCategoriesCommand createConvertToEntity(RoomCategoriesDetail detail) {
        if (detail == null) {
            return null;
        }
        return RoomCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .important(detail.getImportant())
                .configBed(detail.getConfigBed())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(RoomCategoriesCommand entity, RoomCategoriesDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setConfigBed(detail.getConfigBed());
        entity.setImportant(detail.getImportant());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public RoomCategoriesDetail convertToDetail(RoomCategoriesCommand entity) {
        return (RoomCategoriesDetail) convertToInfo(entity);
    }

    @Override
    public RoomCategoriesInfo convertToInfo(RoomCategoriesCommand entity) {
        if (entity == null) {
            return null;
        }

        return RoomCategoriesInfo.builder()
                .id(entity.getRoomCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .important(entity.getImportant())
                .configBed(entity.getConfigBed())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<RoomCategoriesCommand> optional = roomCategoriesRepository.findByRoomCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_ROOM_CATEGORIES);
        }
        return optional.get().getId();
    }
}
