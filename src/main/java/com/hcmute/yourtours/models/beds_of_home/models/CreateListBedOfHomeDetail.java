package com.hcmute.yourtours.models.beds_of_home.models;

import com.hcmute.yourtours.models.beds_of_home.BedOfHomeDetail;
import lombok.Data;

import java.util.List;

@Data
public class CreateListBedOfHomeDetail {
    private List<BedOfHomeDetail> listBedOfHomeDetail;
}
