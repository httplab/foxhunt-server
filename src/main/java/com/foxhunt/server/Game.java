package com.foxhunt.server;

import com.foxhunt.core.entity.*;
import com.foxhunt.core.packets.EnvironmentUpdatePacketD;
import com.foxhunt.core.packets.FoxhuntPacket;
import com.foxhunt.core.packets.GameEventPacketD;
import com.foxhunt.server.events.EnvironmentUpdateRequest;
import com.foxhunt.server.events.FoxhuntEvent;
import com.foxhunt.server.events.PlayerMovedEvent;
import com.foxhunt.server.netty.FoxhuntConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 19.02.12
 * Time: 2:16
 * To change this template use File | Settings | File Templates.
 */
public class Game implements World.WorldUpdateListener
{
    final static Logger Log = LoggerFactory.getLogger(Game.class);

    private ArrayList<Fox> foxes = new ArrayList<Fox>();
    ArrayList<Integer> playersForEnvUpdate = new ArrayList<Integer>();
    private Queue<FoxhuntEvent> _incomingQueue = new LinkedList<FoxhuntEvent>();
    private long lastFoxUpdateTime = 0;
    private World world;

    public Game()
    {
        //foxes.add(Fox.HOME());
        //foxes.add(Fox.YANDEX());
        //foxes.add(new MovingFox(0,55.721402,37.601960,Fox.RED_FOX, "Zayats",60000, 0.001))
        generateFoxes(55.841656,37.655582, 55.847041,37.672812,50);
    }



    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        world.setUpdateListener(this);
    }

    private void ProcessEvents()
    {
        while(!_incomingQueue.isEmpty())
        {
            FoxhuntEvent event = _incomingQueue.remove();
            if(event instanceof PlayerMovedEvent)
            {
                int playerId = ((PlayerMovedEvent) event).getPlayerId();
                GeoSpot location =((PlayerMovedEvent) event).getLastKnownLocation();
                Log.info(String.format("Player ID %1d; moved to %2s;", playerId, (GeoPoint) location));
            }
            else if(event instanceof EnvironmentUpdateRequest)
            {
                int PlayerId = ((EnvironmentUpdateRequest) event).getPlayerId();
                playersForEnvUpdate.add(PlayerId);
            }
        }
    }

    private FoxhuntPacket getEnvironmentUpdatePacket(int playerId)
    {
        Fox[] foxesArr = foxes.toArray(new Fox[foxes.size()]);
        
        Integer[] connected = FoxhuntConnection.getConnected();
        Player[] playersArr = new Player[connected.length-1];
        int j=0;
        for(int i=0; i<connected.length; i++)
        {
            if(connected[i]!=playerId)
            {
                GeoSpot spot = world.getLastKnownPosition(connected[i]);
                playersArr[j] = new Player(
                        connected[i],
                        spot.getLatitude(),
                        spot.getLongitude(),
                        spot.getRadius(),
                        0,
                        "Player" + connected[i].toString());
                j++;
            }
        }

        EnvironmentUpdatePacketD packet = new EnvironmentUpdatePacketD(foxesArr, playersArr);
        return packet;
    }

    @Override
    public void OnUpdate(int playerId, GeoSpot lastKnownLocation) {
        PlayerMovedEvent event = new PlayerMovedEvent();
        event.setSender(this);
        event.setLastKnownLocation(lastKnownLocation);
        event.setPlayerId(playerId);
        _incomingQueue.add(event);
    }

    public void EnqueueEvent(FoxhuntEvent event)
    {
        _incomingQueue.add(event);
    }

    private void GameLoop()
    {
        while (true)
        {
            long currTime = new Date().getTime();
            
            playersForEnvUpdate.clear();
            ProcessEvents();
            if(currTime-lastFoxUpdateTime > 1500)
            {
                for(Fox fox : foxes)
                {
                    if(fox instanceof MovingFox)
                    {
                        ((MovingFox)fox).Update();
                    }
                }

                Integer[] onlinePls = FoxhuntConnection.getConnected();
                for(int i=0; i<onlinePls.length; i++)
                {
                    playersForEnvUpdate.add(onlinePls[i]);
                }
                lastFoxUpdateTime = currTime;
            }

            for (Integer playerId : playersForEnvUpdate)
            {
                FoxhuntPacket packet = getEnvironmentUpdatePacket(playerId);
                FoxhuntConnection connection = FoxhuntConnection.GetConnection(playerId);
                if(connection!=null)
                {
                    try
                    {
                        connection.SendPackage(packet);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }

            try{
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                break;
            }
        }
    }

    public Runnable getGameLoop()
    {
        return  new Runnable() {
            @Override
            public void run() {
                GameLoop();
            }
        };
    }


    private void generateFoxes(double lat1, double lon1, double lat2, double lon2, int N)
    {
        Random r = new Random();
        for(int i=0; i<N; i++)
        {
            double lat = r.nextDouble()*(lat2-lat1) + lat1;
            double lon = r.nextDouble()*(lon2-lon1) + lon1;
            Fox fox = new Fox(i,lat, lon, r.nextInt(3), "Fox" + Integer.toString(i) );
            foxes.add(fox);
        }
    }

}
