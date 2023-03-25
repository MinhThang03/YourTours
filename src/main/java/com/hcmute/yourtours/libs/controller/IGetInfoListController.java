package com.hcmute.yourtours.libs.controller;

import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.util.List;

/**
 * IGetListInfoController interface
 *
 * @param <I> id of model
 * @param <T> info model
 * @see IGetInfoListWithFilterController
 */
@RequestMapping("/")
public interface IGetInfoListController<I extends Serializable, T extends BaseData<I>> {

    /**
     * getList method
     *
     * @return list info model
     */
    @GetMapping("list")
    ResponseEntity<BaseResponse<List<T>>> getInfoList();
}
