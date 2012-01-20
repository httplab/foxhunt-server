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
	private double latitude;
	private double longitude;
	private double accuracy;
	private double altitude;
	private double bearing;
	private double speed;
	private long clientTime;
	private long fixTime;
	private byte provider;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	private int userId;

	public double getAccuracy()
	{
		return accuracy;
	}

	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}

	public double getAltitude()
	{
		return altitude;
	}

	public void setAltitude(double altitude)
	{
		this.altitude = altitude;
	}

	public double getBearing()
	{
		return bearing;
	}

	public void setBearing(double bearing)
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

	public long getFixTime()
	{
		return fixTime;
	}

	public void setFixTime(long fixTime)
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

	public byte getProvider()
	{
		return provider;
	}

	public void setProvider(byte provider)
	{
		this.provider = provider;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	@Override public String toString()
	{
		return String.format("user: %1d; lon: %2f; lat: %3f",userId,longitude,latitude);
	}
}
