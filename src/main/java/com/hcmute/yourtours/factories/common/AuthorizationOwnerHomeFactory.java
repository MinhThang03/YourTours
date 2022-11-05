package com.hcmute.yourtours.factories.common;

import com.hcmute.yourtours.config.AuditorAwareImpl;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.owner_of_home.IOwnerOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationOwnerHomeFactory implements IAuthorizationOwnerHomeFactory {

    private final AuditorAwareImpl auditorAware;
    private final IOwnerOfHomeFactory iOwnerOfHomeFactory;

    public AuthorizationOwnerHomeFactory(AuditorAwareImpl auditorAware, IOwnerOfHomeFactory iOwnerOfHomeFactory) {
        this.auditorAware = auditorAware;
        this.iOwnerOfHomeFactory = iOwnerOfHomeFactory;
    }

    @Override
    public void checkOwnerOfHome(UUID homeId) throws InvalidException {
        UUID userId = auditorAware.checkUnAuthorization();
        boolean check = iOwnerOfHomeFactory.existByOwnerIdAndHomeId(userId, homeId);
        if (!check) {
            throw new InvalidException(YourToursErrorCode.FORBIDDEN);
        }
    }
}
