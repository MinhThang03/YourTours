package com.hcmute.yourtours.controllers.app;

import com.hcmute.yourtours.controllers.app.interfaces.IItemFavoritesController;
import com.hcmute.yourtours.factories.homes.IHomesFactory;
import com.hcmute.yourtours.factories.homes.app.IAppHomesFactory;
import com.hcmute.yourtours.libs.controller.BaseController;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import com.hcmute.yourtours.models.common.SuccessResponse;
import com.hcmute.yourtours.models.homes.HomeDetail;
import com.hcmute.yourtours.models.homes.HomeInfo;
import com.hcmute.yourtours.models.item_favorties.ItemFavoritesDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/app/favorites")
@Tag(name = "APP API: FAVORITES", description = "API xử lý danh mục yêu thích của khách hàng")
@Transactional
public class AppItemFavoriteController
        extends BaseController<UUID, HomeInfo, HomeDetail>
        implements IItemFavoritesController {

    private final IAppHomesFactory iHomesFactory;

    protected AppItemFavoriteController
            (
                    @Qualifier("appHomesFactory") IHomesFactory iDataFactory,
                    IResponseFactory iResponseFactory,
                    IAppHomesFactory iHomesFactory
            ) {
        super(iDataFactory, iResponseFactory);
        this.iHomesFactory = iHomesFactory;
    }

    @Override
    @Operation(summary = "Thêm hoặc xóa một mục yêu thích")
    public ResponseEntity<BaseResponse<SuccessResponse>> handleFavorites(ItemFavoritesDetail detail) {
        try {
            LogContext.push(LogType.REQUEST, detail);
            SuccessResponse response = iHomesFactory.handleFavorites(detail);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<BasePagingResponse<HomeInfo>>> getPageFavorites(Integer number, Integer size) {
        try {
            BasePagingResponse<HomeInfo> response = iHomesFactory.getFavoritesListOfCurrentUser(number, size);
            LogContext.push(LogType.RESPONSE, response);
            return iResponseFactory.success(response);
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }
}
