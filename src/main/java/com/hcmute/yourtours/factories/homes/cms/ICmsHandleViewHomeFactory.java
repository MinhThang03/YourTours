package com.hcmute.yourtours.factories.homes.cms;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.homes.models.HostHomeDetailModel;

import java.util.UUID;

public interface ICmsHandleViewHomeFactory {
    HostHomeDetailModel getDetailByHomeId(UUID homeId) throws InvalidException;
}
