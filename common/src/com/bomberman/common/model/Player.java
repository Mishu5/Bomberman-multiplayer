package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.PLAYER_TEXTURE;

public class Player extends MapObject {

    private int playerID;

    public Player(int positionX, int positionY, int playerID) {
        super(positionX, positionY, true, true, PLAYER_TEXTURE);
        this.playerID = playerID;
    }
}