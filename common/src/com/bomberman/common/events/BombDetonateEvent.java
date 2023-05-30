package com.bomberman.common.events;

import com.badlogic.gdx.scenes.scene2d.Event;

public class BombDetonateEvent extends Event {
    private final int posX;
    private final int posY;
    private final int radius;

    public BombDetonateEvent(int posX, int posY, int radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
    }

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public int getRadius() { return radius; }
}
