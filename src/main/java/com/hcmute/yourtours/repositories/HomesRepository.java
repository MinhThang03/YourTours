package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.HomesCommand;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomesRepository extends JpaRepository<HomesCommand, Long> {
    Optional<HomesCommand> findByHomeId(UUID homeID);

    @Query(nativeQuery = true,
            value = "select a.* " +
                    "from homes a " +
                    "         inner join owner_of_home b on a.home_id = b.home_id " +
                    "where a.deleted = false " +
                    "  and (b.user_id = :userId or :userId is null) ",
            countQuery = "select a.id " +
                    "from homes a " +
                    "         inner join owner_of_home b on a.home_id = b.home_id " +
                    "where a.deleted = false " +
                    "  and (b.user_id = :userId or :userId is null) ")
    Page<HomesCommand> findPageWithFilter(@Param(":userId") UUID userId, PageRequest pageRequest);
}
