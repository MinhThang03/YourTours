package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.ItemFavoritesCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemFavoritesRepository extends JpaRepository<ItemFavoritesCommand, Long> {
    Optional<ItemFavoritesCommand> findByItemFavoritesId(UUID itemFavoritesId);

    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);

    @Modifying
    void deleteByUserIdAndHomeId(UUID userId, UUID homeId);

    Optional<ItemFavoritesCommand> findByUserIdAndHomeId(UUID userId, UUID homeId);
}
