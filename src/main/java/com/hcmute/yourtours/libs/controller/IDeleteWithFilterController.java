package com.hcmute.yourtours.libs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;

import javax.validation.Valid;

/**
 * IDeleteWithFilterController interface
 *
 * @param <I> id of model
 * @param <R> filter extends {@link BaseFilter}
 */
@RequestMapping("/")
public interface IDeleteWithFilterController<I, R extends BaseFilter> {
    /**
     * Delete method
     *
     * @param data extends {@link BaseFilter}
     * @return {@link FactoryDeleteResponse}
     */
    @PostMapping("delete")
    ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelWithFilter(@Valid @RequestBody R data);
}
