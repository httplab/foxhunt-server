package com.foxhunt.server.Events;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 09.03.12
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class EnvironmentUpdateRequest extends FoxhuntEvent
{
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    private int playerId;
}
