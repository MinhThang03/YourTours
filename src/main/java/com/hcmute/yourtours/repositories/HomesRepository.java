package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.commands.HomesCommand;
import com.hcmute.yourtours.models.province.ProvinceProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomesRepository extends JpaRepository<HomesCommand, Long> {
    Optional<HomesCommand> findByHomeId(UUID homeID);

    @Query(nativeQuery = true,
            value = "select a.*  " +
                    "from homes a  " +
                    "         inner join owner_of_home b on a.home_id = b.home_id  " +
                    "where a.deleted = false  " +
                    "  and (b.user_id = :userId or :userId is null)  " +
                    "order by case  " +
                    "             when :sortBy = 'VIEW' then a.view  " +
                    "             when :sortBy = 'FAVORITE' then a.favorite  " +
                    "             else a.created_date  " +
                    "             end desc  ",
            countQuery = "select a.id " +
                    "from homes a " +
                    "         inner join owner_of_home b on a.home_id = b.home_id " +
                    "where a.deleted = false " +
                    "  and (b.user_id = :userId or :userId is null) ")
    Page<HomesCommand> findPageWithFilter(@Param(":userId") UUID userId,
                                          @Param("sortBy") String sortBy,
                                          PageRequest pageRequest);


    @Query
            (
                    nativeQuery = true,
                    value = "select count(a.id) as numberBooking, a.province_code as provinceCode " +
                            "from homes a, " +
                            "     book_home b " +
                            "where a.home_id = b.home_id " +
                            "group by a.province_code " +
                            "order by count(a.id) desc ",
                    countQuery = "select count(a.id) as numberBooking, a.province_code as provinceCode " +
                            "from homes a, " +
                            "     book_home b " +
                            "where a.home_id = b.home_id " +
                            "group by a.province_code " +
                            "order by count(a.id) desc "
            )
    Page<ProvinceProjection> getProvinceWithFilter(Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "select a.*  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and a.status = :status ",
            countQuery = "select a.id  " +
                    "from homes a,  " +
                    "     item_favorites b  " +
                    "where a.home_id = b.home_id  " +
                    "  and b.user_id = :userId  " +
                    "  and a.status = :status "
    )
    Page<HomesCommand> getFavoritesListByUserId(@Param("userId") UUID userId,
                                                Pageable pageable);
}
