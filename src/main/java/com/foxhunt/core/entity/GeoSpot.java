package com.foxhunt.core.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 11.03.12
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class GeoSpot extends GeoPoint{
    protected double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public GeoSpot(double latitude, double longitude, double radius)
    {
        super(latitude,longitude);
        this.radius = radius;
    }

    public GeoSpot()
    {

    }

    @Override
    public String toString() {
        return String.format("(%1.6f; %2.6f; %3.0f)", latitude, longitude, radius);
    }
}
