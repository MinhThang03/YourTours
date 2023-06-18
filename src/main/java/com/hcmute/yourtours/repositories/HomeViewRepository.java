package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.HomeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomeViewRepository extends JpaRepository<HomeView, UUID> {

    Optional<HomeView> findByHomeIdAndMonthAndYear(UUID homeId, Integer month, Integer year);
}
