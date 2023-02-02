package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.DiscountCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface DiscountCampaignRepository extends JpaRepository<DiscountCampaign, UUID> {

    Page<DiscountCampaign> findAllByDateEndAfter(LocalDate dateEnd, Pageable pageable);
}
