package com.foxhunt.core.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 16.01.12
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class Fix
{
	private double longitude;
	private double latitude;
	private double accuracy;
	private long clientTime;
	private byte providerId;
	private Integer playerId;

	private Double altitude;
	private Double bearing;
	private Double speed;
	private Long fixTime;

	public Integer getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(Integer playerId)
	{
		this.playerId = playerId;
	}

	public double getAccuracy()
	{
		return accuracy;
	}

	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}

	public Double getAltitude()
	{
		return altitude;
	}

	public void setAltitude(Double altitude)
	{
		this.altitude = altitude;
	}

	public Double getBearing()
	{
		return bearing;
	}

	public void setBearing(Double bearing)
	{
		this.bearing = bearing;
	}

	public long getClientTime()
	{
		return clientTime;
	}

	public void setClientTime(long clientTime)
	{
		this.clientTime = clientTime;
	}

	public Long getFixTime()
	{
		return fixTime;
	}

	public void setFixTime(Long fixTime)
	{
		this.fixTime = fixTime;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public byte getProviderId()
	{
		return providerId;
	}

	public void setProviderId(byte providerId)
	{
		this.providerId = providerId;
	}

	public Double getSpeed()
	{
		return speed;
	}

	public void setSpeed(Double speed)
	{
		this.speed = speed;
	}

	@Override public String toString()
	{
		return String.format("%1f; %2f; %3d", getLongitude(), getLatitude(), getClientTime());
	}

    public GeoSpot getGeoSpot()
    {
        return new GeoSpot(latitude, longitude,accuracy);
    }
}
