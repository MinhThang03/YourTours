package com.hcmute.yourtours.models.discount_of_home.models;

import com.hcmute.yourtours.models.discount_home_categories.DiscountHomeCategoryDetail;
import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DiscountOfHomeViewModel implements Serializable {

    private DiscountHomeCategoryDetail category;
    private DiscountOfHomeDetail config;

}
