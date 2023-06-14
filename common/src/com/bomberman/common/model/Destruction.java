package com.bomberman.common.model;


import com.bomberman.common.utils.Pair;

public class Destruction {
    private Pair center;
    private Pair top;
    private Pair bottom;
    private Pair left;
    private Pair right;

    public Destruction(int x, int y, int radius) {
        center = new Pair(x, y);
        top = new Pair(x, y + radius);
        bottom = new Pair(x, y - radius);
        left = new Pair(x - radius, y);
        right = new Pair(x + radius, y);
    }

    public void setBottom(Pair bottom) {
        this.bottom = bottom;
    }

    public void setCenter(Pair center) {
        this.center = center;
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

    public Pair getCenter() {
        return center;
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
}
