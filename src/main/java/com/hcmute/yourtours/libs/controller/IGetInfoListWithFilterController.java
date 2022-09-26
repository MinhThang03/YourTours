package com.hcmute.yourtours.libs.controller;

import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * IGetsFilterController
 *
 * @param <I> id of model
 * @param <T> info model
 * @param <F> extend {@link BaseFilter}
 * @see IGetInfoListController
 */
@RequestMapping("/")
public interface IGetInfoListWithFilterController<I, T extends BaseData<I>, F extends BaseFilter> {

    /**
     * getListWithFilter  method
     *
     * @param filter
     * @return
     */
    @GetMapping("list")
    ResponseEntity<BaseResponse<List<T>>> getInfoListWithFilter(@ParameterObject @Valid F filter);
}
