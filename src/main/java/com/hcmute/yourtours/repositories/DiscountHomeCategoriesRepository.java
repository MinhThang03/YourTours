package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.DiscountHomeCategoriesCommand;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountHomeCategoriesRepository extends JpaRepository<DiscountHomeCategoriesCommand, Long> {
    Optional<DiscountHomeCategoriesCommand> findByDiscountCategoryId(UUID discountCategoryID);

    List<DiscountHomeCategoriesCommand> findAllByStatus(CommonStatusEnum status);
}
