package com.foxhunt.core.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.01.12
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntPacket
{
	protected byte[] rawPacketData;

	protected FoxhuntPacket()
	{
	}

	protected ByteArrayInputStream getPacketDataStream()
	{
		return new ByteArrayInputStream(rawPacketData);
	}

	protected DataInputStream getPacketDataInputStream()
	{
		return new DataInputStream(getPacketDataStream());
	}

	public int getPackageType() throws Exception
	{
		return getPacketDataInputStream().readInt();
	}
	
	public int getStoredPackageSize() throws Exception
	{
		DataInputStream ds = getPacketDataInputStream();
		ds.skip(4);
		return ds.readInt();
	}

	public int getDataSize() throws Exception
	{
		return rawPacketData.length;
	}

	public FoxhuntPacket(byte[] rawData)
	{
		rawPacketData = rawData;
	}

	@Override public String toString()
	{
		try
		{
			return String.format("%1$d:%2$d", getPackageType(), getStoredPackageSize());
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
	}

	public byte[] getRawPacketData()
	{
		return rawPacketData;
	}

	protected static  byte[] getPacketData(int packetType, byte[] data) throws Exception
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(stream);
		os.writeInt(packetType);
		os.writeInt(8+data.length);
		os.write(data);
		os.close();
		return stream.toByteArray();
	}
}
