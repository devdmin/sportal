package com.devdmin.core.geo;


public class GeoCoderService implements GeoService {
    private  String countryName;

    public GeoCoderService(String countryName) {
        this.countryName = countryName;
    }

    public static GeoService newInstance(double lat, double lng) {

        String countryName =  io.restassured.RestAssured.get("http://geonames.org/findNearbyPlaceName?lat={lat}&lng={long}&username=sportalactivation", lat, lng).
                xmlPath().getString("geonames.geoname.countryName");
        return new GeoCoderService(countryName);
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCityName(){
        return countryName;
    }

}
