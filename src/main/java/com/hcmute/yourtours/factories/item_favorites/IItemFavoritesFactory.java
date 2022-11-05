package com.hcmute.yourtours.factories.item_favorites;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesInfo;

import java.util.UUID;

public interface IItemFavoritesFactory extends IDataFactory<UUID, ItemFavoritesInfo, ItemFavoritesDetail> {

    void handleFavorites(ItemFavoritesDetail detail) throws InvalidException;
}
