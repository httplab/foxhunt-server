package com.foxhunt.core.server;

import com.foxhunt.core.entity.Fix;
import org.jboss.netty.channel.*;
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
	private FoxhuntConnection connection;
    final static Logger log = LoggerFactory.getLogger(FoxhuntTopHandler.class);

	@Override public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		this.connection = new FoxhuntConnection(e.getChannel(),world);
        log.info(Integer.toString(this.hashCode()));
	}

	@Override public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
        FoxhuntPacket packet = (FoxhuntPacket) e.getMessage();
        log.info(packet.toString());
		connection.ProcessPacket(packet);
	}
    
    public FoxhuntTopHandler(World w)
    {
        super();
        this.world = w;
    }

	@Override public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		log.error(e.getCause().getMessage());
	}
}
