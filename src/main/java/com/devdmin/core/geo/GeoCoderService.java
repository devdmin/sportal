package com.devdmin.core.geo;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

import java.io.IOException;
import java.math.BigDecimal;

public class GeoCoderService implements GeoService {
    private GeocoderResult result;

    public GeoCoderService(GeocoderResult result) {
        this.result = result;
    }

    public static GeoService newInstance(double lat, double lng) {
        final Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest =
                new GeocoderRequestBuilder()
                        .setLocation(new LatLng(BigDecimal.valueOf(lat), BigDecimal.valueOf(lng)))
                        .setLanguage("en")
                        .getGeocoderRequest();
        GeocodeResponse geocoderResponse = null;
        try {
            geocoderResponse = geocoder.geocode(geocoderRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GeoCoderService(geocoderResponse.getResults().get(0));
    }

    public String getCountryName() {
        return result.getAddressComponents().get(5).getLongName();
    }

    public String getCityName(){
        return result.getAddressComponents().get(3).getLongName();
    }

}
