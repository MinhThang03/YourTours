package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.homes.models.UserHomeDetailModel;

import java.util.UUID;

public interface IAppHandleViewHomeFactory {
    UserHomeDetailModel getDetailByHomeId(UUID homeId) throws InvalidException;
}
