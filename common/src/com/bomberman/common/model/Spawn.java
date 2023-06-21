package com.bomberman.common.model;


import static com.bomberman.common.utils.GraphicUtils.FLOOR_TEXTURE;


public class Spawn extends MapObject {

    public int getSpawnID() {
        return spawnID;
    }

    private final int spawnID;

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    private final int positionX;
    private final int positionY;

    public Spawn(int positionX, int positionY, int spawnID) {

        super(positionX, positionY, false, true, FLOOR_TEXTURE);
        this.spawnID = spawnID;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
