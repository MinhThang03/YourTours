package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.images_home.IImagesHomeFactory;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.stereotype.Service;

@Service
public class CmsHomeFactory extends HomesFactory {

    protected CmsHomeFactory(HomesRepository repository,
                             IImagesHomeFactory iImagesHomeFactory,
                             IRoomsOfHomeFactory iRoomsOfHomeFactory,
                             IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
                             IOwnerOfHomeFactory iOwnerOfHomeFactory) {
        super(repository,
                iImagesHomeFactory,
                iRoomsOfHomeFactory,
                iAmenitiesOfHomeFactory,
                iOwnerOfHomeFactory);
    }
}
