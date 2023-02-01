package com.hcmute.yourtours.factories.security_categories;

import com.hcmute.yourtours.entities.SecurityCategories;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.security_categories.SecurityCategoryDetail;
import com.hcmute.yourtours.models.security_categories.SecurityCategoryInfo;
import com.hcmute.yourtours.repositories.SecurityCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SecurityCategoriesFactory
        extends BasePersistDataFactory<UUID, SecurityCategoryInfo, SecurityCategoryDetail, Long, SecurityCategories>
        implements ISecurityCategoriesFactory {

    private final SecurityCategoriesRepository securityCategoriesRepository;

    protected SecurityCategoriesFactory(SecurityCategoriesRepository repository) {
        super(repository);
        this.securityCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<SecurityCategoryDetail> getDetailClass() {
        return SecurityCategoryDetail.class;
    }

    @Override
    public SecurityCategories createConvertToEntity(SecurityCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return SecurityCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(SecurityCategories entity, SecurityCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public SecurityCategoryDetail convertToDetail(SecurityCategories entity) {
        if (entity == null) {
            return null;
        }
        return SecurityCategoryDetail.builder()
                .id(entity.getSecurityCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public SecurityCategoryInfo convertToInfo(SecurityCategories entity) {
        if (entity == null) {
            return null;
        }
        return SecurityCategoryInfo.builder()
                .id(entity.getSecurityCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<SecurityCategories> optional = securityCategoriesRepository.findBySecurityCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_SECURITY_CATEGORIES);
        }
        return optional.get().getId();
    }
}
