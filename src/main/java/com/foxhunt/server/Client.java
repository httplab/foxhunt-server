package com.foxhunt.server;

import com.foxhunt.core.netty.FoxhuntPackageDecoder;
import com.foxhunt.core.netty.FoxhuntPackageEncoder;
import com.foxhunt.core.packets.ConnectionRequestPacketU;
import com.foxhunt.core.packets.FoxhuntPacket;
import com.foxhunt.server.netty.MessageReceivedHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

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
		String host = "localhost";
		int port = 9003;
		ChannelFactory factory =
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap (factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				LengthFieldBasedFrameDecoder frameDecoder = new LengthFieldBasedFrameDecoder(1024,0,4,0,4);
				LengthFieldPrepender prepender = new LengthFieldPrepender(4,false);

				return Channels.pipeline(prepender, new FoxhuntPackageEncoder(), frameDecoder, new FoxhuntPackageDecoder(), new ClientHandler(
						new MessageReceivedHandler() {
							@Override
							public void MessageReceived(FoxhuntPacket packet) {
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
			channel.write(new ConnectionRequestPacketU("nu-hin","1234","Malevil","+79034310138"));
		} catch (Exception e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public static void PacketReceieved(FoxhuntPacket packet)
	{

	}
}