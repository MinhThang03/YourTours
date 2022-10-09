package com.hcmute.yourtours.factories.guest_categories;

import com.hcmute.yourtours.commands.GuestCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryInfo;
import com.hcmute.yourtours.repositories.GuestCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class GuestCategoriesFactory
        extends BasePersistDataFactory<UUID, GuestCategoryInfo, GuestCategoryDetail, Long, GuestCategoriesCommand>
        implements IGuestCategoriesFactory {

    private final GuestCategoriesRepository guestCategoriesRepository;

    protected GuestCategoriesFactory(GuestCategoriesRepository repository) {
        super(repository);
        this.guestCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<GuestCategoryDetail> getDetailClass() {
        return GuestCategoryDetail.class;
    }

    @Override
    public GuestCategoriesCommand createConvertToEntity(GuestCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return GuestCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(GuestCategoriesCommand entity, GuestCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public GuestCategoryDetail convertToDetail(GuestCategoriesCommand entity) {
        return (GuestCategoryDetail) convertToInfo(entity);
    }

    @Override
    public GuestCategoryInfo convertToInfo(GuestCategoriesCommand entity) {
        if (entity == null) {
            return null;
        }
        return GuestCategoryInfo.builder()
                .id(entity.getGuestCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<GuestCategoriesCommand> optional = guestCategoriesRepository.findByGuestCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_GUEST_CATEGORIES);
        }
        return optional.get().getId();
    }
}
