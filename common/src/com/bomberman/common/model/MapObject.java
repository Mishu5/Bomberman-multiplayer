package com.bomberman.common.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serializable;

import static com.bomberman.common.utils.GraphicUtils.BLOCK_SIZE;

public abstract class MapObject implements Serializable {

    protected boolean destructible;
    protected boolean transparent;
    protected int width, height;
    protected int positionX, positionY;
    protected Texture texture;
    protected String texturePath;

    public MapObject(int positionX, int positionY, boolean destructible, boolean transparent, String texturePath) {
        this.destructible = destructible;
        this.transparent = transparent;
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = BLOCK_SIZE;
        this.height = BLOCK_SIZE;
        this.texturePath = texturePath;
        texture = null;
    }

    private void initTexture() {
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }


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

    synchronized public void draw(SpriteBatch batch) {
        if(texture == null) initTexture();
        batch.draw(texture, positionX * BLOCK_SIZE, positionY * BLOCK_SIZE);
    }
}