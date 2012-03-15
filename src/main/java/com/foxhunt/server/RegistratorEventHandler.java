package com.foxhunt.server;

import com.foxhunt.server.events.FoxhuntEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 12.03.12
 * Time: 1:25
 * To change this template use File | Settings | File Templates.
 */
public class RegistratorEventHandler extends EventHandler{
    final static Logger Log = LoggerFactory.getLogger(Game.class);
    
    @Override
    public boolean HandleEvent(FoxhuntEvent event) {
        Log.info(event.getClass().getSimpleName());
        return false;
    }
}
