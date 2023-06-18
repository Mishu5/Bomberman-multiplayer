package com.bomberman.common.model;

import com.badlogic.gdx.graphics.Texture;
import com.bomberman.common.utils.GraphicUtils;


import static com.bomberman.common.utils.GraphicUtils.SPAWN_TEXTURE;

public class Spawn extends MapObject {

    public int getSpawnID() {
        return spawnID;
    }

    private int spawnID;

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    private int positionX;
    private int positionY;
    public Spawn(int positionX, int positionY, int spawnID) {

        super(positionX, positionY, false, true, SPAWN_TEXTURE);
        this.spawnID = spawnID;
        this.positionX=positionX;
        this.positionY=positionY;
    }
}
