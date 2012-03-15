package com.foxhunt.server;

import com.foxhunt.core.entity.GeoPoint;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 09.03.12
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class GeoMath
{
    protected double scale;
    public static final double EARTH_MEAN_RADIUS = 6371009;
    public static final double EARTH_EQUATORIAL_RADIUS = 6378137;
    public static final double EARTH_POLAR_RADIUS = 6356752.3;
    public static final double EARTH_ECCENTRICITY = 0.0818191908426;

    public static double EquirectangularDistance(double lat1, double lon1, double lat2, double lon2)
    {
        double x = (lon2-lon1) * Math.cos((lat1+lat2)/2);
        double y = (lat2-lat1);
        return Math.sqrt(x*x + y*y) * EARTH_EQUATORIAL_RADIUS;
    }

    public static double EquirectangularDistance(GeoPoint gp1, GeoPoint gp2)
    {
        double x = (gp2.getLongitude()-gp1.getLongitude()) * Math.cos((gp1.getLatitude()+gp2.getLatitude())/2);
        double y = (gp2.getLatitude()-gp1.getLatitude());
        return Math.sqrt(x*x + y*y) * EARTH_EQUATORIAL_RADIUS;
    }
}
