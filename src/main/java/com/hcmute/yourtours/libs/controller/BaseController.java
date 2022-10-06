package com.hcmute.yourtours.libs.controller;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IDataFactory;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.request.BaseDeleteListRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Support using {@link IDataFactory}, {@link IResponseFactory}, {@link LogContext} for basic CRUD a model
 *
 * @param <I> id of model
 * @param <T> info model extends {@link BaseData <I>} have basic properties of model
 * @param <U> detail model extends info model have all properties of model
 * @see IDataFactory
 * @see IResponseFactory
 * @see LogContext
 */
@Slf4j
public abstract class BaseController<I, T extends BaseData<I>, U extends T> {
    protected final IDataFactory<I, T, U> iDataFactory;
    protected final IResponseFactory iResponseFactory;

    protected BaseController(
            IDataFactory<I, T, U> iDataFactory,
            IResponseFactory iResponseFactory
    ) {
        this.iResponseFactory = iResponseFactory;
        this.iDataFactory = iDataFactory;
    }

    /**
     * Create model with request and return it
     * <p>
     *
     * @param request the wrapper of detail model
     * @return ResponseEntity have detail model
     */
    protected ResponseEntity<BaseResponse<U>> factoryCreateModel(FactoryCreateRequest<I, U> request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            U response = iDataFactory.createModel(request.getData());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    /**
     * Update model and return
     *
     * @param request the wrapper of id and detail model
     * @return detail model
     */
    protected ResponseEntity<BaseResponse<U>> factoryUpdateModel(FactoryUpdateRequest<I, U> request) {
        LogContext.push(LogType.REQUEST, request);
        try {
            U response = iDataFactory.updateModel(request.getId(), request.getData());
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    /**
     * Delete model and return true
     *
     * @param id     id of model
     * @param filter extend {@link BaseFilter}
     * @return boolean if deleted return true if else return false
     */
    protected <F extends BaseFilter> ResponseEntity<BaseResponse<FactoryDeleteResponse>> factoryDeleteWithFilter(I id, F filter) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("filter", filter);
        LogContext.push(LogType.REQUEST, map);
        FactoryDeleteResponse response = new FactoryDeleteResponse();
        try {
            response.setSuccess(iDataFactory.deleteModel(id, filter));
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    /**
     * Return a detail model with id
     *
     * @param id of model
     * @return detail model
     */
    protected ResponseEntity<BaseResponse<FactoryGetResponse<I, U>>> factoryGetDetailById(I id) {
        return factoryGetDetailWithFilter(id, null);
    }

    /**
     * Return a detail model with filter
     *
     * @param id     of model
     * @param filter extend {@link BaseFilter}
     * @return detail of model
     */
    protected <F extends BaseFilter> ResponseEntity<BaseResponse<FactoryGetResponse<I, U>>> factoryGetDetailWithFilter(I id, F filter) {
        FactoryGetResponse<I, U> response = new FactoryGetResponse<>();
        try {
            response.setData(iDataFactory.getDetailModel(id, filter));
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    /**
     * Return list info model with filter
     *
     * @param filter extend {@link BaseFilter}
     * @return list of info model
     */
    protected <F extends BaseFilter> ResponseEntity<BaseResponse<List<T>>> factoryGetInfoListWithFilter(F filter) {
        try {
            List<T> response = iDataFactory.getInfoList(filter);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }


    /**
     * Paging list info model
     *
     * @param filter extend {@link BaseFilter}
     * @param number Page number
     * @param size   Page size
     * @return {@link BasePagingResponse} have paging list info model
     */
    protected <F extends BaseFilter> ResponseEntity<BaseResponse<BasePagingResponse<T>>> factoryGetInfoPageWithFilter(F filter, Integer number, Integer size) {
        try {
            BasePagingResponse<T> response = iDataFactory.getInfoPage(filter, number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }


    /**
     *
     * @param ids
     * @return
     */
    protected ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteWithList(@Valid @RequestBody BaseDeleteListRequest<I> ids) {
        LogContext.push(LogType.REQUEST, ids);
        FactoryDeleteResponse response = new FactoryDeleteResponse();
        try {
            response.setSuccess(iDataFactory.deleteListWithIds(ids.getIds()));
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }


}
