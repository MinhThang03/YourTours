package com.hcmute.yourtours.controllers.app.interfaces;

import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface IItemFavoritesController {

    @PostMapping("/handle")
    ResponseEntity<BaseResponse<SuccessResponse>> handleFavorites(@Valid @RequestBody ItemFavoritesDetail detail);

    @GetMapping("/pages")
    ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getPageFavorites
            (
                    @RequestParam(defaultValue = "0") Integer number,
                    @RequestParam(defaultValue = "20") Integer size
            );
}
