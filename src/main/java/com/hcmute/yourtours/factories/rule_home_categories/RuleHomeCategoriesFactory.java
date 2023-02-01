package com.hcmute.yourtours.factories.rule_home_categories;

import com.hcmute.yourtours.entities.RuleHomeCategories;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryDetail;
import com.hcmute.yourtours.models.rule_home_categories.RuleHomeCategoryInfo;
import com.hcmute.yourtours.repositories.RuleHomeCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RuleHomeCategoriesFactory
        extends BasePersistDataFactory<UUID, RuleHomeCategoryInfo, RuleHomeCategoryDetail, Long, RuleHomeCategories>
        implements IRuleHomeCategoriesFactory {

    private final RuleHomeCategoriesRepository ruleHomeCategoriesRepository;

    protected RuleHomeCategoriesFactory(RuleHomeCategoriesRepository repository) {
        super(repository);
        this.ruleHomeCategoriesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<RuleHomeCategoryDetail> getDetailClass() {
        return RuleHomeCategoryDetail.class;
    }

    @Override
    public RuleHomeCategories createConvertToEntity(RuleHomeCategoryDetail detail) {
        if (detail == null) {
            return null;
        }
        return RuleHomeCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(RuleHomeCategories entity, RuleHomeCategoryDetail detail) {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public RuleHomeCategoryDetail convertToDetail(RuleHomeCategories entity) {
        if (entity == null) {
            return null;
        }
        return RuleHomeCategoryDetail.builder()
                .id(entity.getRuleCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public RuleHomeCategoryInfo convertToInfo(RuleHomeCategories entity) {
        if (entity == null) {
            return null;
        }
        return RuleHomeCategoryInfo.builder()
                .id(entity.getRuleCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        Optional<RuleHomeCategories> optional = ruleHomeCategoriesRepository.findByRuleCategoryId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_RULE_CATEGORIES);
        }
        return optional.get().getId();
    }
}
