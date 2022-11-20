package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.OwnerOfHomeCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerOfHomesRepository extends JpaRepository<OwnerOfHomeCommand, Long> {
    Optional<OwnerOfHomeCommand> findByOwnerOfHomeId(UUID ownerOfHomeId);

    boolean existsByUserIdAndHomeId(UUID userId, UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select b.full_name as ownerName   " +
                    "from owner_of_home a,   " +
                    "     user b   " +
                    "where a.user_id = b.userid   " +
                    "  and a.is_main_owner is true   " +
                    "  and a.home_id = :homeId  "
    )
    String getMainOwnerNameOfHome(UUID homeId);
}
