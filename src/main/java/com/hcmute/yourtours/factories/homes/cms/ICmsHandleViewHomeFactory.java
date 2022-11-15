package com.hcmute.yourtours.factories.homes.cms;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.homes.models.HostHomeDetailModel;
import com.hcmute.yourtours.models.homes.models.UpdateAddressHomeModel;
import com.hcmute.yourtours.models.homes.models.UpdateBasePriceHomeModel;
import com.hcmute.yourtours.models.homes.models.UpdateBaseProfileHomeModel;

import java.util.UUID;

public interface ICmsHandleViewHomeFactory {
    HostHomeDetailModel getDetailByHomeId(UUID homeId) throws InvalidException;

    HostHomeDetailModel updateProfile(UUID homeId, UpdateBaseProfileHomeModel detail) throws InvalidException;

    HostHomeDetailModel updatePrice(UUID homeId, UpdateBasePriceHomeModel detail) throws InvalidException;

    HostHomeDetailModel updateAddress(UUID homeId, UpdateAddressHomeModel detail) throws InvalidException;

}
