package org.ignas.datagenerator.dto;

public class LocationDTO {

    private String latitude;
    private String longtitude;

    public LocationDTO(String latitude, String longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }
}
