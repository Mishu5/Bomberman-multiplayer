package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.BOMB_TEXTURE;

public class Bomb extends MapObject {

    private final int bombRadius;
    private final int timer;

    public Bomb(int positionX, int positionY, int bombRadius, int timer) {
        super(positionX, positionY, false, false, BOMB_TEXTURE);
        this.bombRadius = bombRadius;
        this.timer = timer;
    }

    public int getBombRadius() {
        return this.bombRadius;
    }
    public int getTimer() { return this.timer; }
}