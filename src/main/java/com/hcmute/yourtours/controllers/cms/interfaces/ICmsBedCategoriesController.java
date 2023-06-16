package com.hcmute.yourtours.controllers.cms.interfaces;

import com.hcmute.yourtours.libs.controller.*;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.bed_categories.BedCategoryDetail;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;
import com.hcmute.yourtours.models.bed_categories.filter.BedCategoryFilter;
import com.hcmute.yourtours.models.bed_categories.filter.CmsBedCategoryFilter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

public interface ICmsBedCategoriesController extends
        ICreateModelController<UUID, BedCategoryDetail>,
        IUpdateModelController<UUID, BedCategoryDetail>,
        IGetDetailByIdController<UUID, BedCategoryDetail>,
        IGetInfoPageController<UUID, BedCategoryInfo, CmsBedCategoryFilter>,
        IDeleteModelByIdController<UUID> {

    @GetMapping("page/room-id")
    ResponseEntity<BaseResponse<BasePagingResponse<BedCategoryInfo>>> getInfoPageWithRoomId(
            @ParameterObject @Valid BedCategoryFilter filter,
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "20") Integer size
    );
}
