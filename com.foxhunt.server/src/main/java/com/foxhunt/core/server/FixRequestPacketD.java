package com.foxhunt.core.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.02.12
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class FixRequestPacketD extends FoxhuntPacket
{
	@Override public int getPackageType()
	{
		return 3;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override public String toString()
	{
		return "Fix request";
	}

	@Override protected void Serialize(DataOutputStream stream) throws IOException
	{

	}
}
