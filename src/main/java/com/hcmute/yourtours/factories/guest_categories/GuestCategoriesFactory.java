package com.hcmute.yourtours.factories.guest_categories;

import com.hcmute.yourtours.entities.GuestCategories;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryDetail;
import com.hcmute.yourtours.models.guest_categories.GuestCategoryInfo;
import com.hcmute.yourtours.repositories.GuestCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GuestCategoriesFactory
        extends BasePersistDataFactory<UUID, GuestCategoryInfo, GuestCategoryDetail, UUID, GuestCategories>
        implements IGuestCategoriesFactory {


    protected GuestCategoriesFactory(GuestCategoriesRepository repository) {
        super(repository);
    }

    @Override
    @NonNull
    protected Class<GuestCategoryDetail> getDetailClass() {
        return GuestCategoryDetail.class;
    }

    @Override
    public GuestCategories createConvertToEntity(GuestCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return GuestCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(GuestCategories entity, GuestCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public GuestCategoryDetail convertToDetail(GuestCategories entity) {
        if (entity == null) {
            return null;
        }
        return GuestCategoryDetail.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public GuestCategoryInfo convertToInfo(GuestCategories entity) {
        if (entity == null) {
            return null;
        }
        return GuestCategoryInfo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }


}
