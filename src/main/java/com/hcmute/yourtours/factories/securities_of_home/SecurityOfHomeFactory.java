package com.hcmute.yourtours.factories.securities_of_home;

import com.hcmute.yourtours.commands.SecuritiesOfHomeCommand;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.securities_of_home.SecurityOfHomeDetail;
import com.hcmute.yourtours.models.securities_of_home.SecurityOfHomeInfo;
import com.hcmute.yourtours.repositories.SecuritiesOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityOfHomeFactory
        extends BasePersistDataFactory<UUID, SecurityOfHomeInfo, SecurityOfHomeDetail, Long, SecuritiesOfHomeCommand>
        implements ISecuritiesOfHomeFactory {

    private final SecuritiesOfHomeRepository securitiesOfHomeRepository;

    protected SecurityOfHomeFactory(SecuritiesOfHomeRepository repository) {
        super(repository);
        this.securitiesOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<SecurityOfHomeDetail> getDetailClass() {
        return SecurityOfHomeDetail.class;
    }

    @Override
    public SecuritiesOfHomeCommand createConvertToEntity(SecurityOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return SecuritiesOfHomeCommand.builder()
                .isHave(detail.getIsHave())
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(SecuritiesOfHomeCommand entity, SecurityOfHomeDetail detail) throws InvalidException {
        entity.setIsHave(detail.getIsHave());
        entity.setHomeId(detail.getHomeId());
        entity.setCategoryId(detail.getCategoryId());
    }

    @Override
    public SecurityOfHomeDetail convertToDetail(SecuritiesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SecurityOfHomeDetail.builder()
                .id(entity.getCategoryId())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public SecurityOfHomeInfo convertToInfo(SecuritiesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return SecurityOfHomeInfo.builder()
                .id(entity.getCategoryId())
                .categoryId(entity.getCategoryId())
                .homeId(entity.getHomeId())
                .build();
    }
}
