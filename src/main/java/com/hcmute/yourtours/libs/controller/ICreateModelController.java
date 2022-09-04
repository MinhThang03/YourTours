package com.hcmute.yourtours.libs.controller;

import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.request.FactoryCreateRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @param <I> id of model
 * @param <U> detail model extends {@link BaseData}
 */
@RequestMapping("/")
public interface ICreateModelController<I, U extends BaseData<I>> {
    /**
     * Post method create model
     *
     * @param request Wrapper of Detail Model
     * @return {@link ResponseEntity} have detail model
     */
    @PostMapping("create")
    ResponseEntity<BaseResponse<U>> createModel(@RequestBody @Valid FactoryCreateRequest<I, U> request);
}
