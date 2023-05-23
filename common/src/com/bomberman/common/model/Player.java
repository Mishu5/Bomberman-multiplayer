package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.bomberman.common.utils.GraphicUtils.PLAYER_TEXTURE;

public class Player extends MapObject {

    private int playerID;

    public Player(int positionX, int positionY, int playerID) {
        super(positionX, positionY, true, true, PLAYER_TEXTURE);
        this.playerID = playerID;
    }

    public void move(float x, float y) {
        sprite.setX(sprite.getX() + x);
        sprite.setY(sprite.getY() + y);
    }

    /**
     * Player moves in different way than other objects
     * tick is per pixel instead block sizes
     * so the draw method is different also
     */
    @Override
    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }
}