package com.foxhunt.core.packets;

import com.foxhunt.core.entity.Fox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 19.02.12
 * Time: 1:56
 * To change this template use File | Settings | File Templates.
 */
public class EnvironmentUpdatePacketD extends FoxhuntPacket
{
	private Fox[] foxes;
	private long serverTime;

	@Override public int getPackageType()
	{
		return ENVIRONMENT_UPDATE_D;
	}

    public EnvironmentUpdatePacketD(Fox[] foxes)
    {
        this.foxes = foxes;
        serverTime = new Date().getTime();
    }

	@Override protected void Serialize(DataOutputStream stream) throws IOException
	{
		stream.writeLong(serverTime);
		stream.writeInt(foxes.length);
		for(int i=0; i<foxes.length; i++)
		{
			stream.write(foxes[i].Serialize());
		}
	}

	public EnvironmentUpdatePacketD(DataInputStream stream) throws IOException
	{
		serverTime = stream.readLong();
		int count = stream.readInt();
		foxes = new Fox[count];
		for(int i=0; i<count; i++)
		{
			foxes[i] = Fox.Deserialize(stream);
		}
	}

	@Override public String toString()
	{
		return "Environment Update";
	}
}
