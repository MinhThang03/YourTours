package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;

import java.io.Serializable;
import java.util.List;


public interface IDataFactory<I extends Serializable, T extends BaseData<I>, U extends T> {

    U createModel(U detail) throws InvalidException;

    U updateModel(I id, U detail) throws InvalidException;

    <F extends BaseFilter> boolean deleteModel(I id, F data) throws InvalidException;

    boolean deleteListWithIds(List<I> ids) throws InvalidException;

    <F extends BaseFilter> List<T> getInfoList(F filter) throws InvalidException;

    List<T> getInfoList() throws InvalidException;

    <F extends BaseFilter> U getDetailModel(I id, F filter) throws InvalidException;

    <F extends BaseFilter> BasePagingResponse<T> getInfoPage(F filter, Integer number, Integer size) throws InvalidException;

    <F extends BaseFilter> boolean existByFilter(I id, F filter) throws InvalidException;

    boolean existByDetail(U detail) throws InvalidException;
}
