package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.DiscountCampaignCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountCampaignRepository extends JpaRepository<DiscountCampaignCommand, Long> {
    Optional<DiscountCampaignCommand> findByDiscountCampaignId(UUID discountCampaignId);
}
