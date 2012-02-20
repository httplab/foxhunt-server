package com.foxhunt.server;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 17.02.12
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public enum ConnectionState
{
	New,
	Authenticated,
	Error,
	Closing,
	Closed
}
