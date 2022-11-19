package com.hcmute.yourtours.models.surcharges_of_home.models;

import com.hcmute.yourtours.models.surcharges_of_home.SurchargeOfHomeDetail;
import lombok.Data;

import java.util.List;

@Data
public class CreateListSurchargeHomeModel {
    private List<SurchargeOfHomeDetail> listSurchargeHomeDetail;
}
