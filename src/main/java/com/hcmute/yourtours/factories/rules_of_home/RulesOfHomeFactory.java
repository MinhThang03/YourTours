package com.hcmute.yourtours.factories.rules_of_home;

import com.hcmute.yourtours.entities.RulesOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
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
        extends BasePersistDataFactory<UUID, RuleOfHomeInfo, RuleOfHomeDetail, Long, RulesOfHomeCommand>
        implements IRulesOfHomeFactory {

    private final RulesOfHomeRepository rulesOfHomeRepository;

    protected RulesOfHomeFactory(RulesOfHomeRepository repository) {
        super(repository);
        this.rulesOfHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<RuleOfHomeDetail> getDetailClass() {
        return RuleOfHomeDetail.class;
    }

    @Override
    public RulesOfHomeCommand createConvertToEntity(RuleOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return RulesOfHomeCommand.builder()
                .isHave(detail.getIsHave())
                .ruleHomeId(detail.getRuleHomeId())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(RulesOfHomeCommand entity, RuleOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setRuleHomeId(detail.getRuleHomeId());
        entity.setIsHave(detail.getIsHave());
    }

    @Override
    public RuleOfHomeDetail convertToDetail(RulesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RuleOfHomeDetail.builder()
                .isHave(entity.getIsHave())
                .ruleHomeId(entity.getRuleOfHomeId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public RuleOfHomeInfo convertToInfo(RulesOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return RuleOfHomeInfo.builder()
                .isHave(entity.getIsHave())
                .ruleHomeId(entity.getRuleOfHomeId())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        RulesOfHomeCommand command = rulesOfHomeRepository.findByRuleHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_RULES_OF_HOME);
        }
        return command.getId();
    }
}
