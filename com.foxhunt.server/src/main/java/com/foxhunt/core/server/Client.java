package com.foxhunt.core.server;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 20.01.12
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
public class Client
{
	public static void main(String[] arguments)
	{
		String host = "dev.httplab.ru";
		int port = 9003;
		ChannelFactory factory =
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap (factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(new FoxhuntPackageEncoder(),new FoxhuntFrameDecoder(), new ClientHandler(
						new MessageReceivedHandler()
						{
							@Override public void MessageReceived(FoxhuntPacket packet)
							{
								Client.PacketReceieved(packet);
							}
						}
				));
			}
		});
		bootstrap.setOption("tcpNoDelay" , true);
		bootstrap.setOption("keepAlive", true);
		ChannelFuture connectWait = bootstrap.connect(new InetSocketAddress(host, port));
		connectWait = connectWait.awaitUninterruptibly();
		Channel channel = connectWait.getChannel();

		try
		{
			channel.write(new ConnectionRequestPacketU("nu-hin","1234","Malville","+79034310138"));
		} catch (Exception e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public static void PacketReceieved(FoxhuntPacket packet)
	{

	}
}
