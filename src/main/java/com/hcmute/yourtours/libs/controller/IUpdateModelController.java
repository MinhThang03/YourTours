package com.hcmute.yourtours.libs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.request.FactoryUpdateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;

import javax.validation.Valid;

/**
 * IUpdateModelController interface
 *
 * @param <I> id of model
 * @param <U> detail model
 */
@RequestMapping("/")
public interface IUpdateModelController<I, U extends BaseData<I>> {

    /**
     * updateModel
     *
     * @param request {@link FactoryUpdateRequest} wrapper model
     * @return detail model
     */
    @PutMapping("update")
    ResponseEntity<BaseResponse<U>> updateModel(@RequestBody @Valid FactoryUpdateRequest<I, U> request);
}
