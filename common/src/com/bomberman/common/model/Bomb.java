package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.BOMB_TEXTURE;

public class Bomb extends MapObject {
    public Bomb(int positionX, int positionY) {
        super(3,positionX, positionY, false, false, BOMB_TEXTURE);
    }
}