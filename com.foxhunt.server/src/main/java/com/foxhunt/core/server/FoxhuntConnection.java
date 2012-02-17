package com.foxhunt.core.server;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.Channels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.02.12
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntConnection
{
	final static Logger log = LoggerFactory.getLogger(FoxhuntServer.class);

	private Integer playerId;
	private ConnectionState connectionState = ConnectionState.New;
	private Channel channel;

	public void ProcessPacket(FoxhuntPacket packet) throws Exception
	{
		switch (packet.getPackageType())
		{
			case 0:
				if(connectionState == ConnectionState.New)
				{
					int res = PerformAuthentication( (ConnectionRequestPacketU) packet);
					if(res==-1)
					{
						connectionState = ConnectionState.Error;
						SendPackage(new AuthResultPacketD(false,"Authentication failed"));
					}
					else
					{
						playerId=res;
						connectionState = ConnectionState.Authenticated;
						SendPackage(new AuthResultPacketD(true,"OK"));
					}
				}
				else
				{
					log.error("Wrong state");
					connectionState = ConnectionState.Error;
					throw new Exception("Wrong state");
				}
				break;
			default:
				connectionState = ConnectionState.Error;
		}
	}

	private int PerformAuthentication(ConnectionRequestPacketU packet)
	{
		if(packet.getLogin().equals("nu-hin"))
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

	public void SendPackage(FoxhuntPacket packet) throws Exception
	{
		byte[] data = packet.Serialize();
		ChannelBuffer buf = ChannelBuffers.buffer(data.length);
		buf.writeBytes(data);
		Channels.write(channel,buf);
	}

	public FoxhuntConnection(Channel channel) throws Exception
	{
		if(channel==null)
			throw new Exception();
		this.channel = channel;
	}
}
