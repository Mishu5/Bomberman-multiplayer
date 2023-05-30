package com.bomberman.common.events;

import com.badlogic.gdx.scenes.scene2d.Event;

public class PlayerDisconnectEvent extends Event {
    private int playerID;
    private int posX;
    private int posY;
    public PlayerDisconnectEvent(int playerID, int posX, int posY) {
        this.playerID = playerID;
        this.posX = posX;
        this.posY = posY;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
