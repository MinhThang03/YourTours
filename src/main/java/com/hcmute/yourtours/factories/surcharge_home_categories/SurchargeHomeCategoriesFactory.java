package com.hcmute.yourtours.factories.surcharge_home_categories;

import com.hcmute.yourtours.entities.SurchargeHomeCategories;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoryDetail;
import com.hcmute.yourtours.models.surcharge_home_categories.SurchargeHomeCategoryInfo;
import com.hcmute.yourtours.repositories.SurchargeHomeCategoriesRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SurchargeHomeCategoriesFactory
        extends BasePersistDataFactory<UUID, SurchargeHomeCategoryInfo, SurchargeHomeCategoryDetail, UUID, SurchargeHomeCategories>
        implements ISurchargeHomeCategoriesFactory {


    private final SurchargeHomeCategoriesRepository surchargeHomeRepository;

    protected SurchargeHomeCategoriesFactory(SurchargeHomeCategoriesRepository repository,
                                             SurchargeHomeCategoriesRepository surchargeHomeRepository) {
        super(repository);
        this.surchargeHomeRepository = surchargeHomeRepository;
    }

    @Override
    @NonNull
    protected Class<SurchargeHomeCategoryDetail> getDetailClass() {
        return SurchargeHomeCategoryDetail.class;
    }

    @Override
    public SurchargeHomeCategories createConvertToEntity(SurchargeHomeCategoryDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return SurchargeHomeCategories.builder()
                .name(detail.getName())
                .description(detail.getDescription())
                .status(detail.getStatus())
                .build();
    }

    @Override
    public void updateConvertToEntity(SurchargeHomeCategories entity, SurchargeHomeCategoryDetail detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDescription(detail.getDescription());
        entity.setStatus(detail.getStatus());
    }

    @Override
    public SurchargeHomeCategoryDetail convertToDetail(SurchargeHomeCategories entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SurchargeHomeCategoryDetail.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public SurchargeHomeCategoryInfo convertToInfo(SurchargeHomeCategories entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SurchargeHomeCategoryInfo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }

    @Override
    protected <F extends BaseFilter> void aroundDelete(UUID id, F filter) throws InvalidException {
        if (id != null && surchargeHomeRepository.existForeignKey(id)) {
            surchargeHomeRepository.softDelete(id);
        }
        super.aroundDelete(id, filter);
    }
}
