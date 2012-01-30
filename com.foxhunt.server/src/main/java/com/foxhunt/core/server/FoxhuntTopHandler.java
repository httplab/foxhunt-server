package com.foxhunt.core.server;

import com.foxhunt.core.entity.Fix;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.01.12
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntTopHandler extends SimpleChannelHandler
{
    private final World world;
    final static Logger log = LoggerFactory.getLogger(FoxhuntTopHandler.class);

	@Override public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
        log.info(Integer.toString(this.hashCode()));
	}

	@Override public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
	   FoxhuntPacket packet = (FoxhuntPacket) e.getMessage();
		
       log.info(packet.toString());
	}
    
    public FoxhuntTopHandler(World w)
    {
        super();
        this.world = w;
    }
}
