package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.UserEvaluate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEvaluateRepository extends JpaRepository<UserEvaluate, Long> {
    Optional<UserEvaluate> findByUserEvaluateId(UUID userEvaluateId);

    Optional<UserEvaluate> findByUserIdAndHomeId(UUID userId, UUID homeId);

    List<UserEvaluate> findAllByHomeIdAndPointIsNotNull(UUID homeId);

    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from user_evaluate a  " +
                    "where case  " +
                    "          when :typeFilter = 'RATE' then a.point is not null  " +
                    "          when :typeFilter = 'COMMENT' then a.comment is not null  " +
                    "          else '1' = '1' end  " +
                    "  and (:homeId is null or :homeId = a.home_id)",
            countQuery = "select a.id  " +
                    "from user_evaluate a  " +
                    "where case  " +
                    "          when :typeFilter = 'RATE' then a.point is not null  " +
                    "          when :typeFilter = 'COMMENT' then a.comment is not null  " +
                    "          else '1' = '1' end  " +
                    "  and (:homeId is null or :homeId = a.home_id) "
    )
    Page<UserEvaluate> findPageWithEvaluateFilter(@Param("typeFilter") String typeFilter,
                                                  @Param("homeId") UUID homeId,
                                                  Pageable pageable);
}
