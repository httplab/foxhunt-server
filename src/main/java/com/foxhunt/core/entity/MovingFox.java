package com.foxhunt.core.entity;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 09.03.12
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public class MovingFox extends Fox{
    private double originLat;
    private double originLon;
    private double amplitude;
    private long period;

    public MovingFox(int id, double latitude, double longitude, int type, String name, long period, double amplitude)
    {
        super(id,latitude,longitude,type,name);
        originLat = latitude;
        originLon = longitude;
        this.period = period;
        this.amplitude = amplitude;
    }

    public void Update()
    {
        double p = (double)(new Date().getTime() % period) / period * 2* Math.PI;
        setLatitude(originLat+amplitude*Math.sin(p));
    }

}
