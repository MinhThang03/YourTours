package com.hcmute.yourtours.factories.province;

import com.hcmute.yourtours.entities.Province;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import com.hcmute.yourtours.models.province.ProvinceModel;
import com.hcmute.yourtours.models.province.ProvinceProjection;
import com.hcmute.yourtours.repositories.ProvinceRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvinceFactory extends BasePersistDataFactory<Long, ProvinceModel, ProvinceModel, Long, Province> implements IProvinceFactory {

    private final ProvinceRepository provinceRepository;

    protected ProvinceFactory(ProvinceRepository repository) {
        super(repository);
        this.provinceRepository = repository;
    }

    @Override
    public BasePagingResponse<ProvinceProjection> getProvinceSortByNumberBooking(Integer number, Integer size) {
        Page<ProvinceProjection> page = provinceRepository.getProvinceSortByNumberBooking(PageRequest.of(number, size));

        return new BasePagingResponse<>(
                page.getContent(),
                number,
                size,
                page.getTotalElements()
        );
    }

    @Override
    public ProvinceModel getProvinceByCodeName(String provinceCode) throws InvalidException {
        Optional<Province> province = provinceRepository.findByCodeName(provinceCode);
        if (province.isEmpty()) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_PROVINCE);
        }
        return convertToDetail(province.get());
    }

    @Override
    @NonNull
    protected Class<ProvinceModel> getDetailClass() {
        return ProvinceModel.class;
    }

    @Override
    public Province createConvertToEntity(ProvinceModel detail) throws InvalidException {
        return Province.builder()
                .codeName(detail.getCodeName())
                .divisionType(detail.getDivisionType())
                .thumbnail(detail.getThumbnail())
                .name(detail.getName())
                .build();
    }

    @Override
    public void updateConvertToEntity(Province entity, ProvinceModel detail) throws InvalidException {
        entity.setName(detail.getName());
        entity.setDivisionType(detail.getDivisionType());
        entity.setThumbnail(detail.getThumbnail());
    }

    @Override
    public ProvinceModel convertToDetail(Province entity) throws InvalidException {
        return convertToInfo(entity);
    }

    @Override
    public ProvinceModel convertToInfo(Province entity) throws InvalidException {
        return ProvinceModel.builder()
                .codeName(entity.getCodeName())
                .divisionType(entity.getDivisionType())
                .thumbnail(entity.getThumbnail())
                .name(entity.getName())
                .build();
    }

    @Override
    protected <F extends BaseFilter> Iterable<Province> listQuery() {
        return repository.findAll(Sort.by("name"));
    }
}
