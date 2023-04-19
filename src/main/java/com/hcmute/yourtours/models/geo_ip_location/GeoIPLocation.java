package com.hcmute.yourtours.models.geo_ip_location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class GeoIPLocation {

    private String cityName;
    private String countryName;
}
