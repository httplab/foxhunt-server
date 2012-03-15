package com.foxhunt.server.events;

import com.foxhunt.core.entity.Fox;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 11.03.12
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class FoxTakenEvent extends FoxhuntEvent{
    private Fox fox;

    public Fox getFox() {
        return fox;
    }

    public void setFox(Fox fox) {
        this.fox = fox;
    }
}
