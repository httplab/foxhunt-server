package com.foxhunt.core.entity;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 18.02.12
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public class Fox
{
    public static Fox HOME()
    {
        Fox res = new Fox();
        res.setId(0);
        res.setLatitude(55.721439);
        res.setLongitude(37.601927);
        res.setType(RED_FOX);
        res.setName("Pionerskaya");
        return res;
    }

    public static Fox YANDEX()
    {
        Fox res = new Fox();
        res.setId(1);
        res.setLatitude(55.734009);
        res.setLongitude(37.588473);
        res.setType(BLUE_FOX);
        res.setName("Russkaya");
        return  res;
    }

	public final static int RED_FOX = 0;
	public final static int BLUE_FOX = 1;
	public final static int GRAY_FOX = 2;

	private int id;
	private double latitude;
	private double longitude;
	private String name;
	private int type;

    public Fox()
    {
        
    }
    
    public Fox(int id, long latitude, long longitude, int type, String name)
    {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.name = name;
    }
    
    
    
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public byte[] Serialize() throws IOException
	{
		ByteArrayOutputStream str = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(str);
		dos.writeInt(id);
		dos.writeDouble(latitude);
		dos.writeDouble(longitude);
		dos.writeUTF(name);
		dos.writeInt(type);
		dos.close();
		return str.toByteArray();
	}
	
	public static Fox Deserialize(DataInputStream stream) throws IOException
	{
		Fox res = new Fox();
		
		res.id = stream.readInt();
		res.latitude = stream.readDouble();
		res.longitude = stream.readDouble();
		res.name = stream.readUTF();
		res.type = stream.readInt();

		return res;
	}
}
