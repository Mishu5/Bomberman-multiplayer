package com.bomberman.common.events;

import java.io.Serializable;

public class PlayerDisconnectEvent implements Event, Serializable {
    private final int playerID;
    private final int posX;
    private final int posY;

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

    @Override
    public String toString() {
        return (getPosX() + " " + getPosY() + " " + getPlayerID());
    }

    @Override
    public String getCommand() {
        return "BombDisconnectEvent";
    }
}
