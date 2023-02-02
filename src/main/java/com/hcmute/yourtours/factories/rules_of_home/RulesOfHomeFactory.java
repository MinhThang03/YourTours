package com.hcmute.yourtours.factories.rules_of_home;

import com.hcmute.yourtours.entities.RulesOfHome;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.rules_of_home.RuleOfHomeDetail;
import com.hcmute.yourtours.models.rules_of_home.RuleOfHomeInfo;
import com.hcmute.yourtours.repositories.RulesOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RulesOfHomeFactory
        extends BasePersistDataFactory<UUID, RuleOfHomeInfo, RuleOfHomeDetail, UUID, RulesOfHome>
        implements IRulesOfHomeFactory {

    protected RulesOfHomeFactory(RulesOfHomeRepository repository) {
        super(repository);
    }

    @Override
    @NonNull
    protected Class<RuleOfHomeDetail> getDetailClass() {
        return RuleOfHomeDetail.class;
    }

    @Override
    public RulesOfHome createConvertToEntity(RuleOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return RulesOfHome.builder()
                .isHave(detail.getIsHave())
                .ruleHomeId(detail.getRuleHomeId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(RulesOfHome entity, RuleOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setRuleHomeId(detail.getRuleHomeId());
        entity.setIsHave(detail.getIsHave());
    }

    @Override
    public RuleOfHomeDetail convertToDetail(RulesOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RuleOfHomeDetail.builder()
                .isHave(entity.getIsHave())
                .ruleHomeId(entity.getId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public RuleOfHomeInfo convertToInfo(RulesOfHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RuleOfHomeInfo.builder()
                .isHave(entity.getIsHave())
                .ruleHomeId(entity.getId())
                .homeId(entity.getHomeId())
                .build();
    }

}
