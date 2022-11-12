package com.hcmute.yourtours.factories.homes.cms;

import com.hcmute.yourtours.factories.amenities.IAmenitiesFactory;
import com.hcmute.yourtours.factories.common.IAuthorizationOwnerHomeFactory;
import com.hcmute.yourtours.factories.discount_of_home.IDiscountOfHomeFactory;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.rooms_of_home.IRoomsOfHomeFactory;
import com.hcmute.yourtours.factories.surcharges_of_home.ISurchargeOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.homes.models.HostHomeDetailModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CmsHandleViewHomeFactory implements ICmsHandleViewHomeFactory {

    private final IHomesFactory iHomesFactory;
    private final IRoomsOfHomeFactory iRoomsOfHomeFactory;
    private final ISurchargeOfHomeFactory iSurchargeOfHomeFactory;
    private final IDiscountOfHomeFactory iDiscountOfHomeFactory;
    private final IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory;
    private final IAmenitiesFactory iAmenitiesFactory;

    public CmsHandleViewHomeFactory
            (
                    @Qualifier("cmsHomesFactory") IHomesFactory iHomesFactory,
                    IRoomsOfHomeFactory iRoomsOfHomeFactory,
                    ISurchargeOfHomeFactory iSurchargeOfHomeFactory,
                    IDiscountOfHomeFactory iDiscountOfHomeFactory,
                    IAuthorizationOwnerHomeFactory iAuthorizationOwnerHomeFactory,
                    IAmenitiesFactory iAmenitiesFactory
            ) {
        this.iHomesFactory = iHomesFactory;
        this.iRoomsOfHomeFactory = iRoomsOfHomeFactory;
        this.iSurchargeOfHomeFactory = iSurchargeOfHomeFactory;
        this.iDiscountOfHomeFactory = iDiscountOfHomeFactory;
        this.iAuthorizationOwnerHomeFactory = iAuthorizationOwnerHomeFactory;
        this.iAmenitiesFactory = iAmenitiesFactory;
    }

    @Override
    public HostHomeDetailModel getDetailByHomeId(UUID homeId) throws InvalidException {
        iAuthorizationOwnerHomeFactory.checkOwnerOfHome(homeId);

        return HostHomeDetailModel.builder()
                .homeDetail(iHomesFactory.getDetailModel(homeId, null))
                .discounts(iDiscountOfHomeFactory.getDiscountsOfHomeView(homeId))
                .numberOfRooms(iRoomsOfHomeFactory.getNumberOfRoomCategoryByHomeId(homeId, null))
                .surcharges(iSurchargeOfHomeFactory.getListCategoryWithHomeId(homeId))
                .amenities(iAmenitiesFactory.getLimitTrueByHomeId(homeId))
                .build();
    }
}
