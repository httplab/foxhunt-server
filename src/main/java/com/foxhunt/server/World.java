package com.foxhunt.server;

import com.foxhunt.core.entity.Fix;
import com.foxhunt.core.entity.GeoSpot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventListener;
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
	public interface WorldUpdateListener extends EventListener
    {
        void OnUpdate(int playerId, GeoSpot lastKnownLocation);
    }

    private Queue<Fix> fixQueue;
	private boolean stop = true;
	final static Logger log = LoggerFactory.getLogger(World.class);
	private HashMap<Integer, Fix> lastKnownPositions;
    private WorldUpdateListener updateListener;

    public WorldUpdateListener getUpdateListener() {
        return updateListener;
    }

    public void setUpdateListener(WorldUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void EnqueueFix(Fix fix)
	{
		fixQueue.add(fix);
	}

	public GeoSpot getLastKnownPosition(int playerId)
    {
        return lastKnownPositions.get(playerId).getGeoSpot();
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
            else
            {
                try
                {
                    Thread.sleep(10);
                }
                catch ( InterruptedException e)
                {
                    break;
                }
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
        
        if(updateListener!=null)
        {
            updateListener.OnUpdate(fix.getPlayerId(), fix.getGeoSpot());
        }
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
