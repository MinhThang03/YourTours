package com.hcmute.yourtours.libs.controller;

import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * IGetInfoPageController interface
 *
 * @param <I> id of model
 * @param <T> info model
 * @param <F> extends {@link BaseFilter}
 */
@RequestMapping("/")
public interface IGetInfoPageController<I extends Serializable, T extends BaseData<I>, F extends BaseFilter> {

    /**
     * getPaging method
     *
     * @param filter extends {@link BaseFilter}
     * @param number page number
     * @param size   page size
     * @return {@link BasePagingResponse}
     */
    @GetMapping("page")
    ResponseEntity<BaseResponse<BasePagingResponse<T>>> getInfoPageWithFilter(
            @ParameterObject @Valid F filter,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
