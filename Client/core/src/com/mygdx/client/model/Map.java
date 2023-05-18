package com.mygdx.client.model;

import java.util.ArrayList;

public class Map {
    private int width;
    private int height;
    final private ArrayList<MapObject> objects;
    final private ArrayList<MapObject> walls;

    Map() {
        objects = new ArrayList<>();
        walls = new ArrayList<>();
    }
}
