package com.bomberman.common.model;

import com.badlogic.gdx.graphics.Texture;
import com.bomberman.common.utils.GraphicUtils;


import static com.bomberman.common.utils.GraphicUtils.SPAWN_TEXTURE;

public class Spawn extends MapObject {
    public Spawn(int positionX, int positionY, int spawnId) {
        super(positionX, positionY, false, true, SPAWN_TEXTURE);
    }
}
