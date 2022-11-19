package com.hcmute.yourtours.models.discount_of_home.models;

import com.hcmute.yourtours.models.discount_of_home.DiscountOfHomeDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateListDiscountOfHomeModel implements Serializable {
    private List<DiscountOfHomeDetail> listDiscountOfHomeDetail;
}
