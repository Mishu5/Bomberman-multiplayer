package com.bomberman.common.model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bomberman.common.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;

import static com.bomberman.common.utils.GraphicUtils.*;

public class Destruction implements Serializable {
    private final Pair center;
    private Pair top;
    private Pair bottom;
    private Pair left;
    private Pair right;
    private boolean isTextureInit;
    private boolean isAnimationStarted;
    private final ArrayList<DestructionTexture> destruction;

    public Destruction(int x, int y, int radius) {
        destruction = new ArrayList<>();
        center = new Pair(x, y);
        top = new Pair(x, y + radius);
        bottom = new Pair(x, y - radius);
        left = new Pair(x - radius, y);
        right = new Pair(x + radius, y);
        isTextureInit = false;
        this.isAnimationStarted = false;
    }

    public void setBottom(Pair bottom) {
        this.bottom = bottom;
    }

    public void setLeft(Pair left) {
        this.left = left;
    }

    public void setRight(Pair right) {
        this.right = right;
    }

    public void setTop(Pair top) {
        this.top = top;
    }

    public Pair getBottom() {
        return bottom;
    }

    public Pair getLeft() {
        return left;
    }

    public Pair getRight() {
        return right;
    }

    public Pair getTop() {
        return top;
    }

    private void initTextures() {
        destruction.add(new DestructionTexture(left.first, left.second, DESTRUCTION_LEFT_END));
        destruction.add(new DestructionTexture(right.first, right.second, DESTRUCTION_RIGHT_END));
        destruction.add(new DestructionTexture(top.first, top.second, DESTRUCTION_TOP_END));
        destruction.add(new DestructionTexture(bottom.first, bottom.second, DESTRUCTION_BOTTOM_END));
        for (int i = center.second + 1; i < top.second; i++)
            destruction.add(new DestructionTexture(center.first, i, DESTRUCTION_TOP));
        for (int i = bottom.second + 1; i < center.second; i++)
            destruction.add(new DestructionTexture(center.first, i, DESTRUCTION_BOTTOM));
        for (int i = left.first + 1; i < center.first; i++)
            destruction.add(new DestructionTexture(i, center.second, DESTRUCTION_LEFT));
        for (int i = center.first + 1; i < right.first; i++)
            destruction.add(new DestructionTexture(i, center.second, DESTRUCTION_RIGHT));
        destruction.add(new DestructionTexture(center.first, center.second, DESTRUCTION_CENTER));
        isTextureInit = true;
    }

    synchronized public void draw(SpriteBatch batch) {
        if (!isTextureInit) initTextures();
        destruction.forEach((it) -> {
            batch.draw(it.texture, it.position.first * getBlockSize(), it.position.second * getBlockSize());
        });
    }

    public boolean isAnimationStarted() {
        return isAnimationStarted;
    }

    public void animationStart() {
        this.isAnimationStarted = true;
    }
}

class DestructionTexture implements Serializable {
    Pair position;
    Texture texture;

    DestructionTexture(int x, int y, String texturePath) {
        this.position = new Pair(x, y);
        texture = getTexture(texturePath);
    }
}

