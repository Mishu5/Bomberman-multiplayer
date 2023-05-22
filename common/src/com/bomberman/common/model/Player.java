package com.bomberman.common.model;

public class Player extends MapObject {

    private int playerID;

    public Player(int positionX, int positionY, int playerID) {
        super(positionX, positionY, true, true);
        this.playerID = playerID;
    }

}