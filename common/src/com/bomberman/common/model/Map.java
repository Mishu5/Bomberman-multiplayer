package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Map {

    ArrayList<MapObject> map = new ArrayList<>();
    ArrayList<MapObject> bombs = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

    public void addDestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, true));
    }


    public void addIndestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, false));
    }

    public void addPowerUp(int positionX, int positionY) {
        map.add(new PowerUp(positionX, positionY));
    }

    public void addFloor(int positionX, int positionY) {
        map.add(new Floor(positionX, positionY));
    }


    public void addPlayer(int positionX, int positionY, int playerID) {
        players.add(new Player(positionX, positionY, playerID));
    }

    public void addBomb(int positionX, int positionY) {
        bombs.add(new Bomb(positionX, positionY));
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void draw(SpriteBatch batch) {
        for(MapObject obj: map) obj.draw(batch);
        for(MapObject obj: bombs) obj.draw(batch);
        for(MapObject obj: players) obj.draw(batch);
    }

}