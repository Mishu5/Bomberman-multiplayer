package com.bomberman.common.model;

import static com.bomberman.common.utils.GraphicUtils.FLOOR_TEXTURE;

public class Floor extends MapObject {
    public Floor(int positionX, int positionY) {
        super(1,positionX, positionY, false, true, FLOOR_TEXTURE);
    }
}
