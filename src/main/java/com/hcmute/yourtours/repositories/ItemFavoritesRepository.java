package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.ItemFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemFavoritesRepository extends JpaRepository<ItemFavorites, Long> {
    Optional<ItemFavorites> findByItemFavoritesId(UUID itemFavoritesId);

    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);

    @Modifying
    void deleteByUserIdAndHomeId(UUID userId, UUID homeId);

    List<ItemFavorites> findAllByUserIdAndHomeId(UUID userId, UUID homeId);
}
