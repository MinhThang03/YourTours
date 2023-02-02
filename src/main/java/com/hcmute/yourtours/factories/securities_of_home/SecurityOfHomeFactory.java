package com.hcmute.yourtours.factories.securities_of_home;

import com.hcmute.yourtours.entities.SecuritiesOfHome;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.securities_of_home.SecurityOfHomeDetail;
import com.hcmute.yourtours.models.securities_of_home.SecurityOfHomeInfo;
import com.hcmute.yourtours.repositories.SecuritiesOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SecurityOfHomeFactory
        extends BasePersistDataFactory<UUID, SecurityOfHomeInfo, SecurityOfHomeDetail, UUID, SecuritiesOfHome>
        implements ISecuritiesOfHomeFactory {


    protected SecurityOfHomeFactory(SecuritiesOfHomeRepository repository) {
        super(repository);
    }

    @Override
    @NonNull
    protected Class<SecurityOfHomeDetail> getDetailClass() {
        return SecurityOfHomeDetail.class;
    }

    @Override
    public SecuritiesOfHome createConvertToEntity(SecurityOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return SecuritiesOfHome.builder()
                .isHave(detail.getIsHave())
                .categoryId(detail.getCategoryId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(SecuritiesOfHome entity, SecurityOfHomeDetail detail) throws InvalidException {
        entity.setIsHave(detail.getIsHave());
        entity.setHomeId(detail.getHomeId());
        entity.setCategoryId(detail.getCategoryId());
    }

    @Override
    public SecurityOfHomeDetail convertToDetail(SecuritiesOfHome entity) throws InvalidException {
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
    public SecurityOfHomeInfo convertToInfo(SecuritiesOfHome entity) throws InvalidException {
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
