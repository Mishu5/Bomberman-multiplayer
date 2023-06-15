package com.bomberman.common.events;

import java.io.Serializable;

public class BombDetonateEvent implements Event, Serializable {
    private final int posX;
    private final int posY;
    private final int radius;

    public BombDetonateEvent(int posX, int posY, int radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return (getPosX() + " " + getPosY() + " " + getRadius());
    }

    @Override
    public String getCommand() {
        return "BombDetonateEvent";
    }

}
