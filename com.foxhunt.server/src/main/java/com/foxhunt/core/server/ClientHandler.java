package com.foxhunt.core.server;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 20.01.12
 * Time: 23:38
 * To change this template use File | Settings | File Templates.
 */
public class ClientHandler extends SimpleChannelHandler
{
	public MessageReceivedHandler OnMessageReceieved;
	final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FoxhuntTopHandler.class);
	
	@Override public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		FoxhuntPacket packet = (FoxhuntPacket) e.getMessage();
		log.info(packet.toString());
		if(OnMessageReceieved!=null)
		{
			OnMessageReceieved.MessageReceived(packet);
		}
	}

	@Override public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		super.exceptionCaught(ctx, e);    //To change body of overridden methods use File | Settings | File Templates.
	}

	public ClientHandler(MessageReceivedHandler handler)
	{
		this.OnMessageReceieved = handler;
	}
}
