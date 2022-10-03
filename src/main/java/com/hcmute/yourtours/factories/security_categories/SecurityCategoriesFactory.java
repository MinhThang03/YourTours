package com.hcmute.yourtours.factories.security_categories;

import com.hcmute.yourtours.commands.SecurityCategoriesCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.security_categories.SecurityCategoriesDetail;
import com.hcmute.yourtours.models.security_categories.SecurityCategoriesInfo;
import com.hcmute.yourtours.repositories.SecurityCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SecurityCategoriesFactory
        extends BasePersistDataFactory<UUID, SecurityCategoriesInfo, SecurityCategoriesDetail, Long, SecurityCategoriesCommand>
        implements ISecurityCategoriesFactory {

    private final SecurityCategoriesRepository securityCategoriesRepository;

    protected SecurityCategoriesFactory(SecurityCategoriesRepository repository) {
        super(repository);
        this.securityCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<SecurityCategoriesDetail> getDetailClass() {
        return SecurityCategoriesDetail.class;
    }

    @Override
    public SecurityCategoriesCommand createConvertToEntity(SecurityCategoriesDetail detail) {
        if (detail == null) {
            return null;
        }
        return SecurityCategoriesCommand.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(SecurityCategoriesCommand entity, SecurityCategoriesDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public SecurityCategoriesDetail convertToDetail(SecurityCategoriesCommand entity) {
        return (SecurityCategoriesDetail) convertToInfo(entity);
    }

    @Override
    public SecurityCategoriesInfo convertToInfo(SecurityCategoriesCommand entity) {
        if (entity == null) {
            return null;
        }
        return SecurityCategoriesInfo.builder()
                .id(entity.getSecurityCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<SecurityCategoriesCommand> optional = securityCategoriesRepository.findBySecurityCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_SECURITY_CATEGORIES);
        }
        return optional.get().getId();
    }
}
