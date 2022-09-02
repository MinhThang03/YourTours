package com.hcmute.yourtours.libs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;

/**
 * @param <I> id of model
 */
@RequestMapping("/")
public interface IDeleteModelByIdController<I> {
    /**
     * Delete method for delete model by id
     *
     * @param id id of Model
     * @return {@link FactoryDeleteResponse}
     */
    @DeleteMapping("{id}/delete")
    ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteModelById(@PathVariable I id);
}
