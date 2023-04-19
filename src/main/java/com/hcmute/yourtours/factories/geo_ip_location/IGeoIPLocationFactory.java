package com.hcmute.yourtours.factories.geo_ip_location;

import com.hcmute.yourtours.models.geo_ip_location.GeoIPLocation;

public interface IGeoIPLocationFactory {
    GeoIPLocation getLocationByCurrentIp();

    GeoIPLocation getLocationByIp(String clientIP);
}
