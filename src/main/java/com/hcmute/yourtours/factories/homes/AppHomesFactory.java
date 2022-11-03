package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppHomesFactory extends HomesFactory {

    protected AppHomesFactory
            (
                    HomesRepository repository,
                    IImagesHomeFactory iImagesHomeFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                    IOwnerOfHomeFactory iOwnerOfHomeFactory
            ) {
        super(
                repository,
                iImagesHomeFactory,
                iRoomsOfHomeFactory,
                iAmenitiesOfHomeFactory,
                iOwnerOfHomeFactory
        );
    }

    @Override
    protected <F extends BaseFilter> void preGetDetail(UUID id, F filter) throws InvalidException {
        HomeDetail detail = convertToDetail(findByHomeId(id));
        detail.setView(detail.getView() + 1);
        updateModel(id, detail);
    }
}
