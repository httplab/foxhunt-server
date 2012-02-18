package com.foxhunt.core.server;

import com.foxhunt.core.entity.Fix;
import com.foxhunt.core.server.FoxhuntServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 16.01.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class World
{
	private Queue<Fix> fixQueue;
	private boolean stop = true;
	final static Logger log = LoggerFactory.getLogger(FoxhuntServer.class);
	private HashMap<Integer, Fix> lastKnownPositions;

	public void EnqueueFix(Fix fix)
	{
		fixQueue.add(fix);
	}

	public Fix getLastKnownPosition(int playerId)
	{
		if(lastKnownPositions.containsKey(playerId))
		{
			return lastKnownPositions.get(playerId);
		}
		else
		{
			return null;
		}
	}

	public void FixProcessingLoop()
	{
		log.info("Entered world loop");
		stop=false;
		while (true)
		{
			if(!fixQueue.isEmpty())
			{
				ProcessFix(fixQueue.poll());
			}

			if(stop)
			{
				return;
			}
		}
	}
	
	private void ProcessFix(Fix fix)
	{
		FoxhuntServer.log.info(fix.toString());
		lastKnownPositions.put(fix.getPlayerId(),fix);
	}

	public void Stop()
	{
	 	 if(!stop)
		 {
			 stop = true;
		 }
	}

	public Runnable getFixLoop()
	{
		return new Runnable()
		{
			@Override public void run()
			{
				FixProcessingLoop();
				log.info("Left world loop");
			}
		};
	}

	public World()
	{
		fixQueue = new LinkedList<Fix>();
		lastKnownPositions = new HashMap<Integer, Fix>();
	}
}
