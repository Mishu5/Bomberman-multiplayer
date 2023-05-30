package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.BOMB_TEXTURE;

public class Bomb extends MapObject {

    private int bombRadius;
    public Bomb(int positionX, int positionY, int bombRadius) {
        super(positionX, positionY, false, false, BOMB_TEXTURE);
        this.bombRadius = bombRadius;
    }

    public int getBombRadius() { return this.bombRadius; }
}