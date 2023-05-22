package com.core.game.model;

public class Wall extends MapObject {

    public Wall(int positionX, int positionY, boolean destructible) {
        super(positionX, positionY, destructible, false);
    }

}