package com.foxhunt.server.netty;

import com.foxhunt.core.entity.Fix;
import com.foxhunt.core.packets.*;
import com.foxhunt.server.ConnectionState;
import com.foxhunt.server.FoxhuntServer;
import com.foxhunt.server.Game;
import com.foxhunt.server.World;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

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

	private static HashMap<Integer, FoxhuntConnection> connectionsMap = new HashMap<Integer, FoxhuntConnection>();

	private Integer playerId;
	private ConnectionState connectionState = ConnectionState.New;
	private Channel channel;
	private World world;
	private Game game;
	private long lastFixDate;

	public static boolean IsLoggedIn(int playerId)
	{
		return connectionsMap.containsKey(playerId);
	}

	public static FoxhuntConnection GetConnection(int playerId)
	{
		if(connectionsMap.containsKey(playerId))
		{
			return connectionsMap.get(playerId);
		}
		else
		{
			return null;
		}
	}

	public void ProcessPacket(FoxhuntPacket packet) throws Exception
	{
		switch (packet.getPackageType())
		{
			case FoxhuntPacket.AUTH_REQUEST_U:
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
						FoxhuntConnection existingConnection = GetConnection(res);
						if(existingConnection!=null)
						{
							existingConnection.Close();
						}
						playerId=res;
						connectionsMap.put(playerId,this);
						connectionState = ConnectionState.Authenticated;
						SendPackage(new AuthResultPacketD(true,"OK"));
						SendPackage(new SystemMessagePacketD("Welcome to Foxhunt, " + ( (ConnectionRequestPacketU) packet).getLogin()));
					}
				}
				else
				{
					log.error("Wrong state");
					connectionState = ConnectionState.Error;
					throw new Exception("Wrong state");
				}
				break;
			case FoxhuntPacket.FIX_U:
				if(connectionState == ConnectionState.Authenticated)
				{
					FixPacketU fixPacket = (FixPacketU) packet;
					Fix fix = fixPacket.getFix();
					if(fix.getClientTime()>lastFixDate)
					{
						lastFixDate=fix.getClientTime();
						fix.setPlayerId(playerId);
						world.EnqueueFix(fix);
					}
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

	public FoxhuntConnection(Channel channel, World world, Game game) throws Exception
	{
		if(channel==null)
			throw new Exception();

		if(world==null)
			throw new Exception();


		this.channel = channel;
		this.world = world;
	}

	public void Close()
	{
		this.connectionState = ConnectionState.Closing;
		connectionsMap.remove(playerId);
		playerId = null;
		ChannelFuture cf = this.channel.close();
		cf.addListener(new ChannelFutureListener()
		{
			@Override public void operationComplete(ChannelFuture future) throws Exception
			{
				FoxhuntConnection.this.connectionState = ConnectionState.Closed;
			}
		});
	}

	public void Remove()
	{
		this.connectionState = ConnectionState.Closed;
		connectionsMap.remove(playerId);
		playerId = null;
	}
}
