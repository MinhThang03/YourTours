package com.hcmute.yourtours.factories.item_favorites;

import com.hcmute.yourtours.entities.ItemFavorites;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesInfo;
import com.hcmute.yourtours.repositories.ItemFavoritesRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ItemFavoritesFactory
        extends BasePersistDataFactory<UUID, ItemFavoritesInfo, ItemFavoritesDetail, Long, ItemFavorites>
        implements IItemFavoritesFactory {

    private final ItemFavoritesRepository itemFavoritesRepository;

    protected ItemFavoritesFactory(ItemFavoritesRepository repository) {
        super(repository);
        this.itemFavoritesRepository = repository;
    }

    @Override
    @NonNull
    protected Class<ItemFavoritesDetail> getDetailClass() {
        return ItemFavoritesDetail.class;
    }

    @Override
    public ItemFavorites createConvertToEntity(ItemFavoritesDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return ItemFavorites.builder().homeId(detail.getHomeId()).userId(detail.getUserId()).build();
    }

    @Override
    public void updateConvertToEntity(ItemFavorites entity, ItemFavoritesDetail detail) throws InvalidException {
        entity.setHomeId(detail.getHomeId());
        entity.setUserId(detail.getUserId());
    }

    @Override
    public ItemFavoritesDetail convertToDetail(ItemFavorites entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return ItemFavoritesDetail.builder().id(entity.getItemFavoritesId()).homeId(entity.getHomeId()).userId(entity.getUserId()).build();
    }

    @Override
    public ItemFavoritesInfo convertToInfo(ItemFavorites entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return ItemFavoritesInfo.builder().id(entity.getItemFavoritesId()).homeId(entity.getHomeId()).userId(entity.getUserId()).build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        return findByItemFavoritesId(id).getId();
    }


    private ItemFavorites findByItemFavoritesId(UUID id) throws InvalidException {
        Optional<ItemFavorites> optional = itemFavoritesRepository.findByItemFavoritesId(id);
        if (optional.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_ITEM_FAVORITES);
        }
        return optional.get();
    }


    @Override
    public boolean handleFavorites(ItemFavoritesDetail detail) throws InvalidException {
        List<ItemFavorites> optionals = itemFavoritesRepository.findAllByUserIdAndHomeId(detail.getUserId(), detail.getHomeId());
        if (optionals.isEmpty()) {
            createModel(detail);
            return true;
        } else {
            for (ItemFavorites optional : optionals) {
                deleteModel(optional.getItemFavoritesId(), null);
            }
            return false;
        }
    }

    @Override
    public boolean existByUserIdAndHomeId(UUID userId, UUID homeId) {
        return itemFavoritesRepository.existsByUserIdAndHomeId(userId, homeId);
    }
}
