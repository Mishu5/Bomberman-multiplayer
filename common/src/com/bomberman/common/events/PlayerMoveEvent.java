package com.bomberman.common.events;

import static com.bomberman.common.utils.EngineUtils.*;

public class PlayerMoveEvent implements Event {
    private final int playerID;
    private final int posX;
    private final int posY;
    private final Direction direction;

    public PlayerMoveEvent(int posX, int posY, int playerID, Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.playerID = playerID;
        this.direction = direction;
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
        return (posX + " " + posY + " " + playerID + " " + direction.toString());
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String getCommand() {
        return "PlayerMoveEvent";
    }
}
