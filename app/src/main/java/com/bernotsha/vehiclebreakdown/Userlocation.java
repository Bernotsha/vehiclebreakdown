package com.bernotsha.vehiclebreakdown;

public class Userlocation {
    String latitude,longitude;
    Userlocation()
    {

    }

    public Userlocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
