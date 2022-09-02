package com.hcmute.yourtours.libs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryGetResponse;

/**
 * IGetDetailByIdController interface
 *
 * @param <I> id of model
 * @param <U> detail model
 */
@RequestMapping("/")
public interface IGetDetailByIdController<I, U extends BaseData<I>> {

    /**
     * getDetail method
     *
     * @param id of model
     * @return detail model
     */
    @GetMapping("{id}/detail")
    ResponseEntity<BaseResponse<FactoryGetResponse<I, U>>> getDetailById(@PathVariable I id);
}
