package com.bomberman.common.events;

public class StartGameEvent implements Event {

    private final int playerID;

    public StartGameEvent(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public String getCommand() {
        return "PlayerStartGameEvent";
    }
}
