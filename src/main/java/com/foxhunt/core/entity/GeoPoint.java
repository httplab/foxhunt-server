package com.foxhunt.core.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 11.03.12
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class GeoPoint {
    protected double latitude;
    protected double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public GeoPoint(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPoint()
    {

    }

    @Override
    public String toString() {
        return String.format("%1.6f; %2.6f", latitude, longitude);
    }
}
