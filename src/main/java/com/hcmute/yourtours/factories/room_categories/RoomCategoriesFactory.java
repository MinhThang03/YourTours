package com.hcmute.yourtours.factories.room_categories;

import com.hcmute.yourtours.entities.RoomCategories;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.room_categories.RoomCategoryDetail;
import com.hcmute.yourtours.models.room_categories.RoomCategoryInfo;
import com.hcmute.yourtours.models.room_categories.filter.RoomCategoryFilter;
import com.hcmute.yourtours.repositories.RoomCategoriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RoomCategoriesFactory
        extends BasePersistDataFactory<UUID, RoomCategoryInfo, RoomCategoryDetail, Long, RoomCategories>
        implements IRoomCategoriesFactory {

    protected final RoomCategoriesRepository roomCategoriesRepository;

    protected RoomCategoriesFactory(RoomCategoriesRepository repository) {
        super(repository);
        this.roomCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<RoomCategoryDetail> getDetailClass() {
        return RoomCategoryDetail.class;
    }

    @Override
    public RoomCategories createConvertToEntity(RoomCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return RoomCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .important(detail.getImportant())
                .configBed(detail.getConfigBed())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(RoomCategories entity, RoomCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setConfigBed(detail.getConfigBed());
        entity.setImportant(detail.getImportant());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public RoomCategoryDetail convertToDetail(RoomCategories entity) {
        if (entity == null) {
            return null;
        }

        return RoomCategoryDetail.builder()
                .id(entity.getRoomCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .important(entity.getImportant())
                .configBed(entity.getConfigBed())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public RoomCategoryInfo convertToInfo(RoomCategories entity) {
        if (entity == null) {
            return null;
        }

        return RoomCategoryInfo.builder()
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
        Optional<RoomCategories> optional = roomCategoriesRepository.findByRoomCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_ROOM_CATEGORIES);
        }
        return optional.get().getId();
    }

    @Override
    protected <F extends BaseFilter> Page<RoomCategories> pageQuery(F filter, Integer number, Integer size) {
        RoomCategoryFilter roomCategoryFilter = (RoomCategoryFilter) filter;
        return roomCategoriesRepository.findPageWithFilter(roomCategoryFilter.getImportant(), PageRequest.of(number, size));

    }

    @Override
    public void checkExistByRoomCategoryId(UUID roomCategoryId) throws InvalidException {
        Optional<RoomCategories> optional = roomCategoriesRepository.findByRoomCategoryId(roomCategoryId);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_ROOM_CATEGORIES);
        }
    }
}
