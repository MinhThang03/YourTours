package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        postCreate(entity, detail);
        DT response = convertToDetail(entity);
        return response;
    }

    @Override
    protected DT aroundUpdate(I id, DT detail) throws InvalidException {
        ET oldEntity = getEntity(id, detail).orElseThrow(
                () -> new InvalidException(ErrorCode.NOT_FOUND)
        );
        updateConvertToEntity(oldEntity, detail);
        ET entity = repository.save(oldEntity);
        postUpdate(entity, detail);
        DT response = convertToDetail(entity);
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

        Page<ET> pageEntity = pageQuery(filter, number, size);

        return new BasePagingResponse<>(
                convertList(pageEntity.getContent()),
                pageEntity.getNumber(),
                pageEntity.getSize(),
                pageEntity.getTotalElements()
        );
    }

    @Override
    protected <F extends BaseFilter> List<IF> aroundGetList(F filter) throws InvalidException {
        return convertList(listQuery(filter));
    }

    @Override
    protected List<IF> aroundGetList() throws InvalidException {
        return convertList(listQuery());
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

    protected <F extends BaseFilter> Page<ET> pageQuery(F filter, Integer number, Integer size) throws InvalidException {
        return repository.findAll(PageRequest.of(number, size));
    }

    protected List<IF> convertList(Iterable<ET> entities) throws InvalidException {
        List<IF> response = new ArrayList<>();
        for (ET entity : entities) {
            response.add(convertToInfo(entity));
        }
        return response;
    }

    protected <F extends BaseFilter> Iterable<ET> listQuery(F filter) {
        return repository.findAll();
    }

    protected <F extends BaseFilter> Iterable<ET> listQuery() {
        return repository.findAll();
    }


    @Override
    protected CacheFactoryConfig<DT> cacheFactoryConfig() {
        return new CacheFactoryConfig<>() {
            @Override
            @NonNull
            public Class<DT> objectClass() {
                return getDetailClass();
            }
        };
    }

}
