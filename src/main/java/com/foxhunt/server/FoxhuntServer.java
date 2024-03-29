package com.foxhunt.server;


import com.foxhunt.core.netty.FoxhuntPackageDecoder;
import com.foxhunt.core.netty.FoxhuntPackageEncoder;
import com.foxhunt.server.netty.FoxhuntTopHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
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

	private static World world;
	private static Game game;

	public static World getWorld()
	{
		return world;
	}

	public static Game getGame()
	{
		return game;
	}

	public static void main(String[] arguments)
			throws InterruptedException
	{
		log.info("Server launched");
        world = new World();
		game = new Game();
        game.setWorld(world);
		Thread worldThread = new Thread(world.getFixLoop());
		worldThread.setName("World");
		worldThread.start();
		log.info("World thread started");

        Thread gameThread = new Thread(game.getGameLoop());
        gameThread.setName("Game");
        gameThread.start();
        log.info("Game thread started");


		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory()
		{
			@Override public ChannelPipeline getPipeline() throws Exception
			{
				LengthFieldBasedFrameDecoder frameDecoder = new LengthFieldBasedFrameDecoder(30*1024,0,4,0,4);
				LengthFieldPrepender prepender = new LengthFieldPrepender(4,false);
				return Channels.pipeline(frameDecoder,
						new FoxhuntPackageDecoder(),
						new FoxhuntTopHandler(),
						prepender,
						new FoxhuntPackageEncoder());
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
