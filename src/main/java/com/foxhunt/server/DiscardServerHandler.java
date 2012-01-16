package com.foxhunt.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: sk
 * @date: 01.16.12 14:34
 */
public class DiscardServerHandler extends SimpleChannelHandler {
    final Logger log = LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Channel ch = e.getChannel();
        log.info("Получено сообщение: [{}]", builderMessage((ChannelBuffer) e.getMessage()));
        ch.write(e.getMessage());
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        log.warn("Ошибка: [" + e.getCause().getLocalizedMessage() + "]", e);

        Channel ch = e.getChannel();
        ch.close();
    }

    private StringBuilder builderMessage(ChannelBuffer buf) {
        StringBuilder sb = new StringBuilder();
        while(buf.readable()) {
            sb.append((char) buf.readByte());
        }
        // удалим символы "\r\n"
        sb.setLength(sb.length() - 2);
        return sb;
    }
}