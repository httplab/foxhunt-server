package com.foxhunt.server.Events;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 09.03.12
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class PlayerMovedEvent extends FoxhuntEvent
{
    int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
