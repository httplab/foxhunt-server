package com.foxhunt.server.netty;

import com.foxhunt.core.packets.FoxhuntPacket;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.02.12
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public interface MessageReceivedHandler
{
	void MessageReceived(FoxhuntPacket packet);

}
