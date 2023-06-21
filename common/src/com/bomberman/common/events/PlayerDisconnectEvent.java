package com.bomberman.common.events;

import java.io.Serializable;

public class PlayerDisconnectEvent implements Event, Serializable {
    private final int playerID;

    public PlayerDisconnectEvent(int playerID) {
        this.playerID = playerID;

    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public String toString() {
        return ("Player disconnected " + getPlayerID());
    }

    @Override
    public String getCommand() {
        return "BombDisconnectEvent";
    }
}
