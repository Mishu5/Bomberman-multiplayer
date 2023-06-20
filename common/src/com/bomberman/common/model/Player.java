package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.bomberman.common.utils.GraphicUtils.PLAYER_TEXTURE;

public class Player extends MapObject {

    private int playerID;

    private int movementCoolDown;
    private int bombCoolDown;
    private boolean alive;

    public Player(int positionX, int positionY, int playerID) {
        super(positionX, positionY, true, true, new String(1 + playerID + ".png"));
        this.playerID = playerID;
        this.alive = true;
    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void move(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public boolean movementCoolDownPassed() {
        return movementCoolDown == 0;
    }

    public boolean bombCoolDownPassed() {
        return bombCoolDown == 0;
    }

    public void reduceMovementCoolDown(int val) {
        movementCoolDown -= val;
        if (movementCoolDown < 0) {
            movementCoolDown = 0;
        }
    }

    public void reduceBombCoolDown(int val) {
        bombCoolDown -= val;
        if (bombCoolDown < 0) {
            bombCoolDown = 0;
        }
    }

    public void setMovementCoolDown(int coolDown) {
        movementCoolDown = coolDown;
    }

    public void setBombCoolDown(int coolDown) {
        bombCoolDown = coolDown;
    }

    public int getMovementCoolDown() {
        return movementCoolDown;
    }

    public int getBombCoolDown() {
        return bombCoolDown;
    }

}