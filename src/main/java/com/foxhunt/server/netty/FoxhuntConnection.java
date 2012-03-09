package com.foxhunt.server.netty;

import com.foxhunt.core.entity.Fix;
import com.foxhunt.core.entity.Fox;
import com.foxhunt.core.packets.*;
import com.foxhunt.server.ConnectionState;
import com.foxhunt.server.FoxhuntServer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.Channels;
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
						//Thread.sleep(3000);
						SendPackage(new SystemMessagePacketD("sv10 Welcome to Foxhunt, " + ( (ConnectionRequestPacketU) packet).getLogin()));
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
						FoxhuntServer.getWorld().EnqueueFix(fix);
					}
				}
				break;
            case FoxhuntPacket.ENVIRONMENT_UPDATE_REQUEST_U:
                if(connectionState == ConnectionState.Authenticated)
                {
                    Fox[] ses = new Fox[2];
                    ses[0] = Fox.HOME();
                    ses[1] = Fox.YANDEX();

                    EnvironmentUpdatePacketD p = new EnvironmentUpdatePacketD(ses);
                    SendPackage(p);
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
		Channels.write(channel, packet);
	}

	public FoxhuntConnection(Channel channel) throws Exception
	{
		if(channel==null)
			throw new Exception();

		this.channel = channel;
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
