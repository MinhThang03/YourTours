package com.hcmute.yourtours.factories.common;

import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationOwnerHomeFactory implements IAuthorizationOwnerHomeFactory {

    private final IOwnerOfHomeFactory iOwnerOfHomeFactory;
    private final IGetUserFromTokenFactory iGetUserFromTokenFactory;

    public AuthorizationOwnerHomeFactory(IOwnerOfHomeFactory iOwnerOfHomeFactory,
                                         IGetUserFromTokenFactory iGetUserFromTokenFactory) {
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
        this.iGetUserFromTokenFactory = iGetUserFromTokenFactory;
    }

    @Override
    public void checkOwnerOfHome(UUID homeId) throws InvalidException {
        UUID userId = iGetUserFromTokenFactory.checkUnAuthorization();
        boolean check = iOwnerOfHomeFactory.existByOwnerIdAndHomeId(userId, homeId);
        if (!check) {
            throw new InvalidException(YourToursErrorCode.FORBIDDEN);
        }
    }


}
