package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.*;

public class Bomb extends MapObject {
    private final int bombRadius;
    private int timer;

    public Bomb(int positionX, int positionY, int bombRadius, int timer) {
        super(positionX, positionY, false, false, BOMB_TEXTURE);
        this.bombRadius = bombRadius;
        this.timer = timer;
    }

    public int getBombRadius() {
        return this.bombRadius;
    }

    public void bombTick() {
        texture = null;
        timer = Math.max(0, timer - 1);
        if (timer == 0 || timer > BOMB_TICKS.length) texturePath = BOMB_TEXTURE;
        else texturePath = BOMB_TICKS[timer - 1];
    }

    public int getTimer() {
        return timer;
    }
}