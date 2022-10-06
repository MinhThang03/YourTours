package com.hcmute.yourtours.libs.controller;


import com.hcmute.yourtours.libs.model.factory.request.BaseDeleteListRequest;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.libs.model.factory.response.FactoryDeleteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/")
public interface IDeleteListController<I> {

    /**
     *
     * @param ids
     * @return
     */
    @DeleteMapping("list/delete")
    ResponseEntity<BaseResponse<FactoryDeleteResponse>> deleteWithList(@Valid @RequestBody BaseDeleteListRequest<I> ids);

}
