package com.hcmute.yourtours.factories.price_of_home;

import com.hcmute.yourtours.commands.PriceOfHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeDetail;
import com.hcmute.yourtours.models.price_of_home.PriceOfHomeInfo;
import com.hcmute.yourtours.repositories.PriceOfHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PriceOfHomeFactory
        extends BasePersistDataFactory<UUID, PriceOfHomeInfo, PriceOfHomeDetail, Long, PriceOfHomeCommand>
        implements IPriceOfHomeFactory {

    private final PriceOfHomeRepository priceOfHomeRepository;

    protected PriceOfHomeFactory(
            PriceOfHomeRepository repository,
            PriceOfHomeRepository priceOfHomeRepository) {
        super(repository);
        this.priceOfHomeRepository = priceOfHomeRepository;
    }

    @Override
    @NonNull
    protected Class<PriceOfHomeDetail> getDetailClass() {
        return PriceOfHomeDetail.class;
    }

    @Override
    public PriceOfHomeCommand createConvertToEntity(PriceOfHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return PriceOfHomeCommand.builder()
                .date(detail.getDate())
                .price(detail.getPrice())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(PriceOfHomeCommand entity, PriceOfHomeDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setPrice(detail.getPrice());
        entity.setPrice(detail.getPrice());
    }

    @Override
    public PriceOfHomeDetail convertToDetail(PriceOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return PriceOfHomeDetail.builder()
                .id(entity.getPriceOfHomeId())
                .date(entity.getDate())
                .price(entity.getPrice())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public PriceOfHomeInfo convertToInfo(PriceOfHomeCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return PriceOfHomeInfo.builder()
                .id(entity.getPriceOfHomeId())
                .date(entity.getDate())
                .price(entity.getPrice())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        PriceOfHomeCommand command = priceOfHomeRepository.findByPriceOfHomeId(id).orElse(null);
        if (command == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_PRICE_OF_HOME);
        }
        return command.getId();
    }
}
