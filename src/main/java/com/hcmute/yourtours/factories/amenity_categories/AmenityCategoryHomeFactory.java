package com.hcmute.yourtours.factories.amenity_categories;

import com.hcmute.yourtours.factories.amenities_of_home.IAmenitiesOfHomeFactory;
import com.hcmute.yourtours.factories.common.IAuthorizationOwnerHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.amenities_of_home.projection.AmenityOfHomeModel;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryHomeDetail;
import com.hcmute.yourtours.models.amenity_categories.AmenityCategoryInfo;
import com.hcmute.yourtours.models.amenity_categories.filter.AmenityCategoryFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AmenityCategoryHomeFactory implements IAmenityCategoryHomeFactory {

    private final IAmenityCategoriesFactory iAmenityCategoriesFactory;
    private final IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory;
    private final IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory;

    public AmenityCategoryHomeFactory(
            IAmenityCategoriesFactory iAmenityCategoriesFactory,
            IAmenitiesOfHomeFactory iAmenitiesOfHomeFactory,
            IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory) {
        this.iAmenityCategoriesFactory = iAmenityCategoriesFactory;
        this.iAmenitiesOfHomeFactory = iAmenitiesOfHomeFactory;
        this.iAuthorizationOwnerHomeFactory = iAuthorizationOwnerHomeFactory;
    }

    @Override
    public AmenityCategoryHomeDetail getDetailWithListChild(UUID categoryId, UUID homeId) throws InvalidException {

        iAuthorizationOwnerHomeFactory.checkOwnerOfHome(homeId);

        AmenityCategoryDetail categoryDetail = iAmenityCategoriesFactory.getDetailModel(categoryId, null);
        List<AmenityOfHomeModel> childList = iAmenitiesOfHomeFactory.getAllByCategoryIdAndHomeId(categoryId, homeId);
        return AmenityCategoryHomeDetail.builder()
                .amenityCategory(categoryDetail)
                .childAmenities(childList)
                .build();
    }

    @Override
    public BasePagingResponse<AmenityCategoryHomeDetail> getPageWithChildren(AmenityCategoryFilter filter, Integer number, Integer size) throws InvalidException {
        iAuthorizationOwnerHomeFactory.checkOwnerOfHome(filter.getHomeId());

        BasePagingResponse<AmenityCategoryInfo> infos = iAmenityCategoriesFactory.getInfoPage(filter, number, size);
        List<AmenityCategoryHomeDetail> result = new ArrayList<>();
        for (AmenityCategoryInfo item : infos.getContent()) {
            List<AmenityOfHomeModel> childList = iAmenitiesOfHomeFactory.getAllByCategoryIdAndHomeId(item.getId(), filter.getHomeId());
            result.add(AmenityCategoryHomeDetail.builder()
                    .amenityCategory(item)
                    .childAmenities(childList)
                    .build());
        }
        return new BasePagingResponse<>(
                result,
                infos.getPageNumber(),
                infos.getPageSize(),
                infos.getTotalElements()
        );
    }
}
