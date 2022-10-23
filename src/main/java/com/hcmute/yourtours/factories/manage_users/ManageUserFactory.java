package com.hcmute.yourtours.factories.manage_users;

import com.hcmute.yourtours.commands.UserCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.factories.user.UserFactory;
import com.hcmute.yourtours.keycloak.IKeycloakService;
import com.hcmute.yourtours.libs.configuration.IConfigFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.models.user.UserDetail;
import com.hcmute.yourtours.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ManageUserFactory extends UserFactory implements IManageUserFactory {

    private final UserRepository userRepository;

    protected ManageUserFactory(
            UserRepository repository,
            IKeycloakService iKeycloakService,
            IConfigFactory configFactory) {
        super(repository, iKeycloakService, configFactory);
        this.userRepository = repository;
    }


    @Override
    public UserDetail getDetailByUserName(String userName) throws InvalidException {
        UserCommand entity = userRepository.findByEmail(userName).orElse(null);
        if (entity == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_USER);
        }
        return convertToDetail(entity);
    }

}
