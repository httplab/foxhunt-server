package com.foxhunt.server.events;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 09.03.12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntEvent {
    private int depth;
    private FoxhuntEvent cause;
    private Object Sender;

    public Object getSender() {
        return Sender;
    }

    public void setSender(Object sender) {
        Sender = sender;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public FoxhuntEvent getCause() {
        return cause;
    }

    public void setCause(FoxhuntEvent cause) {
        this.cause = cause;
    }
}
