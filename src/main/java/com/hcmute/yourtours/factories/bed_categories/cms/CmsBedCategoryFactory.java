package com.hcmute.yourtours.factories.bed_categories.cms;

import com.hcmute.yourtours.factories.bed_categories.BedCategoriesFactory;
import com.hcmute.yourtours.factories.beds_of_home.IBedsOfHomeFactory;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.model.factory.response.BasePagingResponse;
import com.hcmute.yourtours.models.bed_categories.BedCategoryInfo;
import com.hcmute.yourtours.models.bed_categories.filter.BedCategoryFilter;
import com.hcmute.yourtours.repositories.BedCategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CmsBedCategoryFactory extends BedCategoriesFactory implements ICmsBedCategoryFactory {

    private final IBedsOfHomeFactory iBedsOfHomeFactory;

    protected CmsBedCategoryFactory(BedCategoriesRepository repository, IBedsOfHomeFactory iBedsOfHomeFactory) {
        super(repository);
        this.iBedsOfHomeFactory = iBedsOfHomeFactory;
    }

    @Override
    public BasePagingResponse<BedCategoryInfo> aroundGetPageWithRoomHomeId(BedCategoryFilter filter, Integer number, Integer size) throws InvalidException {
        List<BedCategoryInfo> resultContent = new ArrayList<>();
        BasePagingResponse<BedCategoryInfo> result = getInfoPage(filter, number, size);
        if (filter.getRoomHomeId() != null) {
            resultContent = result.getContent().stream().map
                    (
                            item -> item.toBuilder()
                                    .numberOfRoom(iBedsOfHomeFactory.countByRoomHomeIdAndCategoryId(filter.getRoomHomeId(), item.getId()))
                                    .build()
                    ).collect(Collectors.toList());
        }

        return new BasePagingResponse<>(
                resultContent,
                result.getPageNumber(),
                result.getPageSize(),
                result.getTotalElements()
        );
    }
}
