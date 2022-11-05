package com.hcmute.yourtours.factories.homes;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;

import java.util.UUID;

public interface IHomesFactory extends IDataFactory<UUID, HomeInfo, HomeDetail> {
    HomesCommand findByHomeId(UUID homeId) throws InvalidException;

    void checkExistsByHomeId(UUID homeId) throws InvalidException;

    SuccessResponse handleFavorites(ItemFavoritesDetail detail) throws InvalidException;

    BasePagingResponse<HomeInfo> getFavoritesListOfCurrentUser(Integer number, Integer size) throws InvalidException;
}
