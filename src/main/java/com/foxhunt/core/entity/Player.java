package com.foxhunt.core.entity;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 09.03.12
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class Player extends GeoSpot
{
    private int id;
    private String name;
    private int type;

    public Player()
    {

    }

    public Player(int id, double latitude, double longitude,double radius, int type, String name)
    {
        super(latitude, longitude, radius);
        this.id = id;
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
        dos.writeDouble(radius);
        dos.close();
        return str.toByteArray();
    }

    public static Player Deserialize(DataInputStream stream) throws IOException
    {
        Player res = new Player();

        res.id = stream.readInt();
        res.latitude = stream.readDouble();
        res.longitude = stream.readDouble();
        res.name = stream.readUTF();
        res.type = stream.readInt();
        res.radius = stream.readDouble();

        return res;
    }


}
