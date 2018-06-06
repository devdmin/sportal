package com.devdmin.cores.geo;

import com.devdmin.core.geo.GeoCoderService;
import com.devdmin.core.geo.GeoService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeoCoderServiceTest {
    @Test
    public void findCountryName(){
        GeoService geoService = new GeoCoderService();
        String countryName = geoService.countryName(52.240688, 21.031488);

        assertEquals("Poland", countryName);
    }
}
