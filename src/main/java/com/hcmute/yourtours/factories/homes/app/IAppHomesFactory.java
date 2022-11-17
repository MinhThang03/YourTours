package com.hcmute.yourtours.factories.homes.app;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.homes.filter.HomeDetailFilter;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import com.hcmute.yourtours.models.user_evaluate.UserEvaluateDetail;

public interface IAppHomesFactory {
    SuccessResponse handleFavorites(ItemFavoritesDetail detail) throws InvalidException;

    BasePagingResponse<HomeInfo> getFavoritesListOfCurrentUser(Integer number, Integer size) throws InvalidException;

    HomeDetail createUserEvaluate(UserEvaluateDetail evaluateDetail) throws InvalidException;

    BasePagingResponse<HomeInfo> getPageWithFullFilter(HomeDetailFilter filter, Integer number, Integer size) throws InvalidException;
}
