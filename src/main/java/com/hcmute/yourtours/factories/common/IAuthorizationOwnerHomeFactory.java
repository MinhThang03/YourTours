package com.hcmute.yourtours.factories.common;

import com.hcmute.yourtours.libs.exceptions.InvalidException;

import java.util.UUID;

public interface IAuthorizationOwnerHomeFactory {
    void checkOwnerOfHome(UUID homeId) throws InvalidException;
}
