package com.hcmute.yourtours.factories.amenities.app;

import com.hcmute.yourtours.commands.AmenitiesCommand;
import com.hcmute.yourtours.factories.amenities.AmenitiesFactory;
import com.hcmute.yourtours.factories.amenity_categories.IAmenityCategoriesFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.amenities.AmenityInfo;
import com.hcmute.yourtours.repositories.AmenitiesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppAmenitiesFactory extends AmenitiesFactory implements IAppAmenitiesFactory {

    protected AppAmenitiesFactory(AmenitiesRepository repository, IAmenityCategoriesFactory iAmenityCategoriesFactory) {
        super(repository, iAmenityCategoriesFactory);
    }

    @Override
    public AmenityInfo convertToInfo(AmenitiesCommand entity) throws InvalidException {
        if (entity == null) {
            return null;
        }

        return AmenityInfo.builder()
                .id(entity.getAmenityId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .icon(entity.getIcon())
                .setFilter(entity.getSetFilter())
                .build();
    }


    @Override
    public BasePagingResponse<AmenityInfo> getPageAmenitiesHaveSetFilter(Integer number, Integer size) throws InvalidException {
        int offset = number * size - 1;
        if (offset < 0) {
            offset = 0;
        }

        List<AmenitiesCommand> contents = new ArrayList<>();

        if (number == 0) {
            contents.add(AmenitiesCommand.builder()
                    .name("Mới")
                    .icon("https://img.icons8.com/ios-filled/25/null/new.png")
                    .build());
        }

        List<AmenitiesCommand> queryResult = amenitiesRepository.getLimitSetFilter(offset, size);

        if (number == 0 && !queryResult.isEmpty()) {
            queryResult.remove(queryResult.size() - 1);
        }

        contents.addAll(queryResult);
        Long total = amenitiesRepository.countLimitSetFilter();

        return new BasePagingResponse<>(
                convertList(contents),
                number,
                size,
                total + 1
        );
    }
}
