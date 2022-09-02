package com.hcmute.yourtours.libs.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class BasePersistDataFactory<I, IF extends BaseData<I>, DT extends IF, ID, ET>
        extends BaseDataFactory<I, IF, DT>
        implements IPersistDataFactory<IF, DT, ET> {

    protected final PagingAndSortingRepository<ET, ID> repository;

    protected BasePersistDataFactory(PagingAndSortingRepository<ET, ID> repository) {
        this.repository = repository;
    }

    @Override
    protected void preCreate(DT detail) throws InvalidException {
        if (existByDetail(detail)) {
            throw new InvalidException(ErrorCode.CONFLICT);
        }
    }

    @Override
    protected DT aroundCreate(DT detail) throws InvalidException {
        ET entity = repository.save(
                createConvertToEntity(detail)
        );
        DT response = convertToDetail(entity);
        postCreate(entity, detail);
        return response;
    }

    @Override
    protected DT aroundUpdate(I id, DT detail) throws InvalidException {
        ET oldEntity = getEntity(id, detail).orElseThrow(
                () -> new InvalidException(ErrorCode.NOT_FOUND)
        );
        updateConvertToEntity(oldEntity, detail);
        ET entity = repository.save(oldEntity);
        DT response = convertToDetail(entity);
        postUpdate(entity, detail);
        return response;
    }

    @Override
    public boolean existByDetail(DT detail) throws InvalidException {
        if (detail.getId() != null) {
            return repository.existsById(convertId(detail.getId()));
        }
        return false;
    }

    @Override
    public <F extends BaseFilter> boolean existByFilter(I id, F filter) throws InvalidException {
        return repository.existsById(convertId(id));
    }

    @Override
    protected <F extends BaseFilter> void aroundDelete(I id, F filter) throws InvalidException {
        repository.deleteById(convertId(id));
    }

    @Override
    protected <F extends BaseFilter> DT aroundGetDetail(I id, F filter) throws InvalidException {
        ET entity = getEntity(id, filter).orElse(null);
        DT detail = convertToDetail(
                entity
        );
        postDetail(entity, detail);
        return detail;
    }

    @Override
    protected <F extends BaseFilter> BasePagingResponse<IF> aroundGetPage(F filter, Integer number, Integer size) throws InvalidException {
        if (filter != null) {
            log.warn("filter not null");
        }
        Page<ET> pageEntity = repository.findAll(PageRequest.of(number, size));
        return new BasePagingResponse<>(
                pageEntity.getContent().stream().map(
                        this::convertToInfo
                ).collect(Collectors.toList()),
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalElements()
        );
    }

    @Override
    protected <F extends BaseFilter> List<IF> aroundGetList(F filter) throws InvalidException {
        if (filter != null) {
            log.warn("filter not null");
        }
        List<IF> response = new ArrayList<>();
        for (ET entity : repository.findAll()) {
            response.add(convertToInfo(entity));
        }
        return response;
    }

    @Override
    protected List<IF> aroundGetList() throws InvalidException {
        List<IF> response = new ArrayList<>();
        for (ET entity : repository.findAll()) {
            response.add(convertToInfo(entity));
        }
        return response;
    }


    protected void postCreate(ET entity, DT detail) throws InvalidException {
    }

    protected Optional<ET> getEntity(I id, DT detail) throws InvalidException {
        return repository.findById(convertId(id));
    }

    protected <F extends BaseFilter> Optional<ET> getEntity(I id, F filter) throws InvalidException {
        return repository.findById(convertId(id));
    }

    protected ID convertId(I id) throws InvalidException {
        try {
            return (ID) id;
        } catch (ClassCastException e) {
            throw new RuntimeException("Cannot cast ModelId to EntityId because they not same type");
        }
    }

    protected void postUpdate(ET entity, DT detail) throws InvalidException {
    }

    protected void postDetail(ET entity, DT detail) throws InvalidException {
    }

}
