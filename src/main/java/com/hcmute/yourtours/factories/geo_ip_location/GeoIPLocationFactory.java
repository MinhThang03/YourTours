package com.hcmute.yourtours.factories.geo_ip_location;

import com.hcmute.yourtours.models.geo_ip_location.GeoIPLocation;
import com.hcmute.yourtours.utils.HttpServletUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
@Slf4j
public class GeoIPLocationFactory implements IGeoIPLocationFactory {

    private static final String GEO_IP_PATH = "GeoLite2-City.mmdb";

    @Override
    public GeoIPLocation getLocationByCurrentIp() {
        return getLocationByIp(HttpServletUtil.getClientIP());
    }

    @Override
    public GeoIPLocation getLocationByIp(String clientIP) {
        try {
            CityResponse cityResponse = getCityResponse(clientIP);
            return GeoIPLocation.builder()
                    .cityName(cityResponse.getMostSpecificSubdivision().getName())
                    .countryName(cityResponse.getCountry().getName())
                    .build();
        } catch (Exception e) {
            log.warn("Could not get GeoLocation by IP={}, message={}", clientIP, e.getMessage());
            return null;
        }
    }

    private CityResponse getCityResponse(String clientIP) throws IOException, GeoIp2Exception {
        Resource resource = new ClassPathResource(GeoIPLocationFactory.GEO_IP_PATH);
        try (DatabaseReader databaseReader = new DatabaseReader.Builder(resource.getInputStream()).build()) {
            InetAddress inetAddress = InetAddress.getByName(clientIP);
            return databaseReader.city(inetAddress);
        }
    }
}
