package com.foxhunt.core.server;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.01.12
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class FoxhuntPacket
{
	public byte[] Serialize() throws IOException
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(stream);
		Serialize(dos);
		dos.close();
		byte[] dataBody = stream.toByteArray();
		stream = new ByteArrayOutputStream();
		dos = new DataOutputStream(stream);
		dos.writeInt(getPackageType());
		dos.writeInt(dataBody.length+8);
		dos.write(dataBody);
		dos.close();
		return stream.toByteArray();
	}

	protected abstract void Serialize(DataOutputStream stream) throws IOException;

	public abstract int getPackageType();

	@Override public String toString()
	{
		try
		{
			return String.format("%1$d", getPackageType());
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
	}

	public static FoxhuntPacket Deserialize(byte[] data)  throws Exception
	{
		ByteInputStream bis = new ByteInputStream(data, data.length);
		DataInputStream dis = new DataInputStream(bis);
		int packageType = dis.readInt();
		int packageLength = dis.readInt();
		FoxhuntPacket res = null;
		switch (packageType)
		{
			case 0:
				res = new ConnectionRequestPacketU(dis);
				break;
			case 1:
				res = new AuthResultPacketD(dis);
				break;
			case 3:
				res = new FixRequestPacketD();
				break;
			case 5:
				res = new EnvironmentUpdateRequestPacketU();
				break;
			default:
				res = null;
		}

		dis.close();
		return res;
	}
}
