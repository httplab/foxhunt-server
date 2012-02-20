package com.foxhunt.server;


import com.foxhunt.core.netty.FoxhuntFrameDecoder;
import com.foxhunt.server.netty.FoxhuntTopHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 16.01.12
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntServer
{
	final static Logger log = LoggerFactory.getLogger(FoxhuntServer.class);
	final static ChannelGroup allChannels = new DefaultChannelGroup("foxhunt-server");
	static boolean shutdown = false;

	public static void main(String[] arguments)
			throws InterruptedException
	{
		log.info("Server launched");
		final World world = new World();
		final Game game = new Game();
		Thread worldThread = new Thread(world.getFixLoop());
		worldThread.setName("World");
		worldThread.start();
		log.info("World thread started");

		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory()
		{
			@Override public ChannelPipeline getPipeline() throws Exception
			{
				return Channels.pipeline(new FoxhuntFrameDecoder(),new FoxhuntTopHandler(world,game));
			}
		});

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);

		Channel channel = bootstrap.bind(new InetSocketAddress(9003));
		log.info("Server bound to port 9003");
		allChannels.add(channel);

		while (true)
		{
			String s = readLine();
			if(s.equals("q"))
			{
				break;
			}
		}

		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();

	    world.Stop();
		log.info("Server stopped");
	}

	public static String readLine()
	{
		String s = "";
		try {
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			s = in.readLine();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return s;
	}


}
