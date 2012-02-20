package com.foxhunt.core.netty;

import com.foxhunt.core.packets.FoxhuntPacket;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 20.01.12
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntFrameDecoder extends FrameDecoder
{
	public static final int MAX_PACKET_LENGTH=1024;
	
	@Override protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception
	{
		if(buffer.readableBytes()<8)
		{
			return null;
		}

		int packageType = buffer.readInt();
		int size = buffer.readInt();
		if(size>MAX_PACKET_LENGTH)
		{
			throw new Exception("Declared packet size exceeds maximum size.");
		}

		buffer.readerIndex(0);

		if(buffer.readableBytes() < size)
		{
			return null;
		}

		byte[] data = buffer.readBytes(size).toByteBuffer().array();

		return FoxhuntPacket.Deserialize(data);
	}
}
