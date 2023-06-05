package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.bomberman.common.utils.GraphicUtils.PLAYER_TEXTURE;

public class Player extends MapObject {

    private int playerID;

    public Player(int positionX, int positionY, int playerID) {
        super(positionX, positionY, true, true, PLAYER_TEXTURE);
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void move(int x, int y) {
        positionX = x;
        positionY = y;
    }
}