package com.bomberman.common.model;

import com.badlogic.gdx.graphics.Texture;
import com.bomberman.common.utils.GraphicUtils;

import static com.bomberman.common.utils.GraphicUtils.FLOOR_TEXTURE;

public class Floor extends MapObject {
    public Floor(int positionX, int positionY) {
        super(positionX, positionY, false, true, FLOOR_TEXTURE);
    }
}
