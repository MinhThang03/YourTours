package com.hcmute.yourtours.factories.guest_categories;

import com.hcmute.yourtours.commands.GuestCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.guest_categories.GuestCategoriesDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoriesInfo;
import com.hcmute.yourtours.repositories.GuestCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class GuestCategoriesFactory
        extends BasePersistDataFactory<UUID, GuestCategoriesInfo, GuestCategoriesDetail, Long, GuestCategoriesCommand>
        implements IGuestCategoriesFactory {

    private final GuestCategoriesRepository guestCategoriesRepository;

    protected GuestCategoriesFactory(GuestCategoriesRepository repository) {
        super(repository);
        this.guestCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<GuestCategoriesDetail> getDetailClass() {
        return GuestCategoriesDetail.class;
    }

    @Override
    public GuestCategoriesCommand createConvertToEntity(GuestCategoriesDetail detail) {
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
    public void updateConvertToEntity(GuestCategoriesCommand entity, GuestCategoriesDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public GuestCategoriesDetail convertToDetail(GuestCategoriesCommand entity) {
        return (GuestCategoriesDetail) convertToInfo(entity);
    }

    @Override
    public GuestCategoriesInfo convertToInfo(GuestCategoriesCommand entity) {
        if (entity == null) {
            return null;
        }
        return GuestCategoriesInfo.builder()
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
