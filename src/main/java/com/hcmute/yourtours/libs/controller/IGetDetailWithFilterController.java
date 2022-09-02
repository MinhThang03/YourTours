package com.hcmute.yourtours.libs.controller;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;

/**
 * IGetDetailWithFilterController
 * <p>
 * Get detail model by filter
 *
 * @param <I> id of model
 * @param <U> detail model
 * @param <R> extends {@link BaseFilter}
 * @see IGetDetailByIdController
 */
@RequestMapping("/")
public interface IGetDetailWithFilterController<I, U extends BaseData<I>, R extends BaseFilter> {

    /**
     * getDetailByFilter method
     *
     * @param data extend filter
     * @return detail model
     */
    @GetMapping("/detail")
    ResponseEntity<BaseResponse<FactoryGetResponse<I, U>>> getDetailWithFilter(@ParameterObject R data);
}
