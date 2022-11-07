package com.hcmute.yourtours.factories.common;

import com.hcmute.yourtours.libs.exceptions.InvalidException;

import java.util.Optional;
import java.util.UUID;

public interface IGetUserFromTokenFactory {
    Optional<String> getCurrentAuditor();

    UUID checkUnAuthorization() throws InvalidException;
}
