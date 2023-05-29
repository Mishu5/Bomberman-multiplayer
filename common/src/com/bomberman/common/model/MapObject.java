package com.bomberman.common.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.bomberman.common.utils.GraphicUtils.BLOCK_SIZE;

public abstract class MapObject {

    protected boolean destructible;
    protected boolean transparent;
    protected int width, height;
    protected int positionX, positionY;
    protected Texture texture;
    protected Sprite sprite;

    public MapObject(int positionX, int positionY, boolean destructible, boolean transparent, String texturePath) {
        this.destructible = destructible;
        this.transparent = transparent;
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = BLOCK_SIZE;
        this.height = BLOCK_SIZE;
        texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture, positionX, positionY, width, height);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }

    public boolean getTransparent(){return this.transparent;}
    public void draw(SpriteBatch batch) {
        this.sprite.setX(this.positionX * BLOCK_SIZE);
        this.sprite.setY(this.positionY * BLOCK_SIZE);
        this.sprite.draw(batch);
    }
}