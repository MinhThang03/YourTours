package com.hcmute.yourtours.repositories;

import com.hcmute.yourtours.entities.Province;
import com.hcmute.yourtours.models.province.ProvinceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    @Query(
            nativeQuery = true,
            value = "select a.* from province a limit 1 "
    )
    List<Province> checkExistData();


    @Query
            (
                    nativeQuery = true,
                    value = "select a.code_name as codeName , " +
                            "       a.name      as name, " +
                            "       a.thumbnail as thumbnail, " +
                            "   coalesce(b.numberBooking, 0) as numberBooking " +
                            "from province a left join  " +
                            "     (select count(a.id) as numberBooking, a.province_code as provinceCode  " +
                            "      from homes a,  " +
                            "           book_home b  " +
                            "      where a.id = b.home_id  " +
                            "      group by a.province_code) b  " +
                            "on a.code_name = b.provinceCode  " +
                            "order by coalesce(b.numberBooking, 0) desc  ",
                    countQuery = "select a.id  " +
                            "from province a left join  " +
                            "     (select count(a.id) as numberBooking, a.province_code as provinceCode  " +
                            "      from homes a,  " +
                            "           book_home b  " +
                            "      where a.id = b.home_id  " +
                            "      group by a.province_code) b  " +
                            "on a.code_name = b.provinceCode  " +
                            "order by coalesce(b.numberBooking, 0) desc  "
            )
    Page<ProvinceProjection> getProvinceSortByNumberBooking(Pageable pageable);
}
