package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.DiscountOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountOfHomeRepository extends JpaRepository<DiscountOfHomeCommand, Long> {
    Optional<DiscountOfHomeCommand> findByDiscountOfHomeId(UUID discountOfHomeId);

    Optional<DiscountOfHomeCommand> findByHomeIdAndCategoryId(UUID homeId, UUID categoryId);

    @Modifying
    void deleteAllByHomeIdAndCategoryId(UUID homeId, UUID categoryId);
}
