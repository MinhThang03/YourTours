package com.hcmute.yourtours.factories.province;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.province.ProvinceModel;
import com.hcmute.yourtours.models.province.ProvinceProjection;
import com.hcmute.yourtours.repositories.HomesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceFactory implements IProvinceFactory {

    private final HomesRepository homesRepository;

    public ProvinceFactory(HomesRepository homesRepository) {
        this.homesRepository = homesRepository;
    }

    @Override
    public BasePagingResponse<ProvinceModel> getListProvinceWithFilter(Integer number, Integer size) {
        Page<ProvinceProjection> page = homesRepository.getProvinceWithFilter(PageRequest.of(number, size));

        List<ProvinceModel> result = new ArrayList<>();

        for (ProvinceProjection item : page) {
            result.add(
                    ProvinceModel
                            .builder()
                            .provinceCode(item.getProvinceCode())
                            .numberBooking(item.getNumberBooking())
                            .build()
            );
        }

        return new BasePagingResponse<>(
                result,
                number,
                size,
                page.getTotalElements()
        );
    }
}
