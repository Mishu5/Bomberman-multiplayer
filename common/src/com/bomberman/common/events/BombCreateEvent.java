package com.bomberman.common.events;

public class BombCreateEvent implements Event {
    private final int posX, posY, radius;

    public BombCreateEvent(int posX, int posY, int radius) {
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
        return (getPosX() + " " + getPosY());
    }

    @Override
    public String getCommand() {
        return "BombCreateEvent";
    }
}
