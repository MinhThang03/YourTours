package com.hcmute.yourtours.libs.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmute.yourtours.libs.cache.TimeRedisCache;
import com.hcmute.yourtours.libs.cache.TimeRedisCacheManager;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class BaseDataFactory<I extends Serializable, IF extends BaseData<I>, DT extends IF> implements IDataFactory<I, IF, DT> {

    @Autowired(required = false)
    protected TimeRedisCacheManager cacheManager;

    @Override
    public DT createModel(DT detail) throws InvalidException {
        preCreate(detail);
        detail = aroundCreate(detail);
//        if (isCache() && cacheFactoryConfig().create() && detail != null) {
//            cachePut(detail.getId(), detail);
//        }
//        clearCollection();
        return detail;
    }

    @Override
    public DT updateModel(I id, DT detail) throws InvalidException {
        preUpdate(id, detail);
        detail = aroundUpdate(id, detail);
//        if (isCache() && cacheFactoryConfig().update() && detail != null) {
//            cachePut(detail.getId(), detail);
//        }
//        clearCollection();
        return detail;
    }

    @Override
    public <F extends BaseFilter> boolean deleteModel(I id, F filter) throws InvalidException {
        preDelete(id, filter);
//        cacheEvict(makeKey(id, filter));
//        clearCollection();
        aroundDelete(id, filter);
        postDelete(id, filter);
        return true;
    }

    @Override
    public boolean deleteListWithIds(List<I> ids) throws InvalidException {
        for (I id : ids) {
            deleteModel(id, null);
        }
        return true;
    }


    @Override
    public <F extends BaseFilter> DT getDetailModel(I id, F filter) throws InvalidException {
//        DT detail = isCache() && cacheFactoryConfig().getDetail() ? cacheGet(makeKey(id, filter), cacheFactoryConfig().objectClass()) : null;
//        if (detail != null) {
//            return detail;
//        }
        preGetDetail(id, filter);
        DT detail = aroundGetDetail(id, filter);
        if (detail == null) {
            throw new InvalidException(ErrorCode.NOT_FOUND);
        }
//        if (isCache() && cacheFactoryConfig().getDetail()) {
//            cachePut(makeKey(id, filter), detail);
//        }
        return detail;
    }

    @Override
    public <F extends BaseFilter> List<IF> getInfoList(F filter) throws InvalidException {
//        List<IF> infos = isCache() && cacheFactoryConfig().getListFilter() ? (List<IF>) cacheListGet(makeKey(null, filter), List.class) : null;
//        if (infos != null) {
//            return infos;
//        }
        preGetList(filter);
        List<IF> infos = aroundGetList(filter);
//        if (isCache() && cacheFactoryConfig().getListFilter()) {
//            cacheListPut(makeKey(null, filter), infos);
//        }
        return infos;
    }

    @Override
    public List<IF> getInfoList() throws InvalidException {
//        List<IF> infos = isCache() && cacheFactoryConfig().getList() ? (List<IF>) cacheListGet(makeKey(null, null), List.class) : null;
//        if (infos != null) {
//            return infos;
//        }
        preGetList();
        List<IF> infos = aroundGetList();
//        if (isCache() && cacheFactoryConfig().getList()) {
//            cacheListPut(makeKey(null, null), infos);
//        }
        return infos;
    }


    @Override
    public <F extends BaseFilter> BasePagingResponse<IF> getInfoPage(F filter, Integer number, Integer size) throws InvalidException {
        BasePagingResponse<IF> response = null;
//        try {
//            response = isCache() && cacheFactoryConfig().getPaging() ? (BasePagingResponse<IF>) cacheListGet(makeKey(null, filter, number, size), BasePagingResponse.class) : null;
//            if (response != null) {
//                return response;
//            }
//        } catch (Exception ignore) {
//        }
        preGetPage(filter, number, size);
        response = aroundGetPage(filter, number, size);
//        if (isCache() && cacheFactoryConfig().getPaging()) {
//            cacheListPut(makeKey(null, filter, number, size), response);
//        }
        return response;
    }


    protected abstract DT aroundCreate(DT detail) throws InvalidException;

    protected abstract void preCreate(DT detail) throws InvalidException;

    protected abstract DT aroundUpdate(I id, DT detail) throws InvalidException;

    protected void preUpdate(I id, DT detail) throws InvalidException {
    }


    protected abstract <F extends BaseFilter> void aroundDelete(I id, F filter) throws InvalidException;

    protected <F extends BaseFilter> void postDelete(I id, F filter) throws InvalidException {
    }

    protected <F extends BaseFilter> void preDelete(I id, F filter) throws InvalidException {
    }

    protected abstract <F extends BaseFilter> DT aroundGetDetail(I id, F filter) throws InvalidException;

    protected <F extends BaseFilter> void preGetDetail(I id, F filter) throws InvalidException {
    }

    protected abstract <F extends BaseFilter> BasePagingResponse<IF> aroundGetPage(F filter, Integer number, Integer size) throws InvalidException;

    protected <F extends BaseFilter> void preGetPage(F filter, Integer page, Integer size) throws InvalidException {
    }

    protected <F extends BaseFilter> void preGetList(F filter) throws InvalidException {
    }

    protected void preGetList() throws InvalidException {
    }


    protected abstract <F extends BaseFilter> List<IF> aroundGetList(F filter) throws InvalidException;

    protected abstract List<IF> aroundGetList() throws InvalidException;


    protected Object makeKey(Object id, Object filter) {
        return makeKey(id, filter, null, null);
    }

    protected Object makeKey(Object id, Object filter, Integer number, Integer size) {
        try {
            if (filter == null && id == null && number == null && size == null) {
                return encode("all");
            }
            if (filter == null) {
                return id;
            }
            Map<String, Object> key = new HashMap<>(4);
            key.put("id", id);
            key.put("filter", filter);
            key.put("number", number);
            key.put("size", size);
            return encode(key);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void cachePut(Object key, Object value) {
        try {
            if (isCache()) {
                TimeRedisCache redisCache = (TimeRedisCache) cacheManager.getCache(cacheFactoryConfig().cacheName());
                if (redisCache != null) {
                    redisCache.put(key, value, cacheFactoryConfig().singleTtl());
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    protected void cacheListPut(Object key, Object values) {
        try {
            if (isCache()) {
                TimeRedisCache redisCache = (TimeRedisCache) cacheManager.getCache(
                        cacheFactoryConfig().cacheNameCollection()
                );
                if (redisCache != null) {
                    redisCache.put(key, values, cacheFactoryConfig().collectionTtl());
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    protected int encode(Object key) throws JsonProcessingException {
        return Objects.hashCode(Objects.requireNonNullElse(key, "all"));
    }

    protected <T> T cacheListGet(Object key, Class<T> responseClass) {
        try {
            if (isCache()) {
                TimeRedisCache cache = (TimeRedisCache) cacheManager.getCache(
                        cacheFactoryConfig().cacheNameCollection()
                );
                if (cache != null) {
                    return cache.get(key, responseClass);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void cacheEvict(Object key) {
        try {
            if (isCache()) {
                Objects.requireNonNull(
                        cacheManager.getCache(
                                cacheFactoryConfig().cacheName()
                        )
                ).evict(key);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected void clearCollection() {
        if (isCache()) {
            Objects.requireNonNull(cacheManager.getCache(cacheFactoryConfig().cacheNameCollection())).clear();
        }
    }

    protected <T> T cacheGet(Object key, Class<T> responseClass) {
        try {
            if (isCache()) {
                TimeRedisCache cache = (TimeRedisCache) cacheManager.getCache(cacheFactoryConfig().cacheName());
                if (cache != null) {
                    return cache.get(key, responseClass);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean isCache() {
        return cacheFactoryConfig() != null && cacheFactoryConfig().cache() && cacheManager != null;
    }

    protected CacheFactoryConfig<DT> cacheFactoryConfig() {
        return new CacheFactoryConfig<>() {
            @Override
            public boolean cache() {
                return false;
            }

            @Override
            @NonNull
            public Class<DT> objectClass() {
                return getDetailClass();
            }
        };
    }

    @NonNull
    protected abstract Class<DT> getDetailClass();

}
