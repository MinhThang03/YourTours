package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.HomeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomeViewRepository extends JpaRepository<HomeView, UUID> {

    @Query(
            nativeQuery = true,
            value = "select a.*      " +
                    "from home_view a      " +
                    "where DATE(a.created_date) = :date      " +
                    "  and a.home_id = :homeId "
    )
    Optional<HomeView> findByDate(UUID homeId, LocalDate date);
}
