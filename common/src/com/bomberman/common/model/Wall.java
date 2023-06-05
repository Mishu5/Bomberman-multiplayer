package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.DESTRUCTIBLE_WALL_TEXTURE;
import static com.bomberman.common.utils.GraphicUtils.WALL_TEXTURE;

public class Wall extends MapObject {
    public Wall(int positionX, int positionY, boolean destructible) {
        super(positionX, positionY, destructible, false, WALL_TEXTURE);
        if(destructible) this.texturePath = DESTRUCTIBLE_WALL_TEXTURE;
    }
}