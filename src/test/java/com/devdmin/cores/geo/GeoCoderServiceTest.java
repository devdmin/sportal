package com.devdmin.cores.geo;

import com.devdmin.core.geo.GeoCoderService;
import com.devdmin.core.geo.GeoService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeoCoderServiceTest {
    @Test
    public void findCountryName(){
        GeoService geoService = GeoCoderService.newInstance(52.240688, 21.031488);
        String countryName = geoService.getCountryName();

        assertEquals("Poland", countryName);
    }

    @Test
    public void findCityName(){
        GeoService geoService = GeoCoderService.newInstance(52.240688, 21.031488);
        String cityName = geoService.getCityName();

        assertEquals("Warszawa", cityName);
    }

}
