package com.hcmute.yourtours.models.homes.filter;

import com.hcmute.yourtours.libs.model.filter.BaseFilter;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class HomeDetailFilter implements BaseFilter {
    private UUID amenityId;
    private Double priceFrom;
    private Double priceTo;
    private Integer numberOfBed;
    private Integer numberOfBedRoom;
    private Integer numberOfBathRoom;
    private List<UUID> amenities;
}
