package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;

import java.util.UUID;

public interface IHomesFactory extends IDataFactory<UUID, HomeInfo, HomeDetail> {
    HomesCommand findByHomeId(UUID homeId) throws InvalidException;

    void checkExistsByHomeId(UUID homeId) throws InvalidException;


}
