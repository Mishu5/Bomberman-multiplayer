package com.bomberman.common.events;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.engine.PlayerHandler.*;

public class BombMoveEvent extends Event {
    private final int posX;
    private final int posY;
    private final Direction direction;


    public BombMoveEvent(int posX, int posY, Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public PlayerHandler.Direction getDirection() {
        return direction;
    }
}
