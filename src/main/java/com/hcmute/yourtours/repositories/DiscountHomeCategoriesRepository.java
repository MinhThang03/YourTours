package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.DiscountHomeCategories;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountHomeCategoriesRepository extends JpaRepository<DiscountHomeCategories, Long> {
    Optional<DiscountHomeCategories> findByDiscountCategoryId(UUID discountCategoryID);

    List<DiscountHomeCategories> findAllByStatus(CommonStatusEnum status);
}
