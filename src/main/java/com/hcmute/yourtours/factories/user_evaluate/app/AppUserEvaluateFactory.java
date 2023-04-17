package com.hcmute.yourtours.factories.user_evaluate.app;

import com.hcmute.yourtours.factories.booking.IBookHomeFactory;
import com.hcmute.yourtours.factories.user.IUserFactory;
import com.hcmute.yourtours.factories.user_evaluate.UserEvaluateFactory;
import com.hcmute.yourtours.repositories.UserEvaluateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppUserEvaluateFactory extends UserEvaluateFactory {

    protected AppUserEvaluateFactory(
            UserEvaluateRepository repository,
            IUserFactory iUserFactory,
            @Qualifier("appBookHomeFactory") IBookHomeFactory iBookHomeFactory
    ) {
        super(repository, iUserFactory, iBookHomeFactory);
    }
}
