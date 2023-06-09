package com.bomberman.common.events;

import static com.bomberman.common.utils.EngineUtils.*;

import java.io.Serializable;

public class BombMoveEvent implements Event, Serializable {
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

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return (getPosX() + " " + getPosY() + " " + getDirection().toString());
    }

    @Override
    public String getCommand() {
        return "BombMoveEvent";
    }
}
