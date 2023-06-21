package com.bomberman.common.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serializable;

import static com.bomberman.common.utils.GraphicUtils.getBlockSize;
import static com.bomberman.common.utils.GraphicUtils.getScaledTexture;

public abstract class MapObject implements Serializable {

    protected boolean destructible;
    protected boolean transparent;
    protected int positionX, positionY;
    protected Texture texture;
    protected String texturePath;

    public MapObject(int positionX, int positionY, boolean destructible, boolean transparent, String texturePath) {
        this.destructible = destructible;
        this.transparent = transparent;
        this.positionX = positionX;
        this.positionY = positionY;
        this.texturePath = texturePath;
        texture = null;
    }
    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() { return positionY; }
    public boolean positionMatch(int x, int y) {
        return positionX == x && positionY == y;
    }
    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public boolean isDestructible() {
        return destructible;
    }
    public boolean isTransparent() {
        return this.transparent;
    }
    private void initTexture() {
        texture = getScaledTexture(texturePath);
    }
    synchronized public void draw(SpriteBatch batch) {
        if(texture == null) initTexture();
        batch.draw(texture, positionX * getBlockSize(), positionY * getBlockSize());
    }
}