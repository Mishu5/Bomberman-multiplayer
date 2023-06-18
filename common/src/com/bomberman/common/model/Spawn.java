package com.bomberman.common.model;

import com.badlogic.gdx.graphics.Texture;
import com.bomberman.common.utils.GraphicUtils;


import static com.bomberman.common.utils.GraphicUtils.SPAWN_TEXTURE;

public class Spawn extends MapObject {

    public int getSpawnID() {
        return spawnID;
    }

    private int spawnID;
    public Spawn(int positionX, int positionY, int spawnID) {

        super(positionX, positionY, false, true, SPAWN_TEXTURE);
        this.spawnID = spawnID;
    }
}
