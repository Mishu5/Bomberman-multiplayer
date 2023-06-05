package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Map {
    private final ArrayList<MapObject> map = new ArrayList<>();
    private final ArrayList<Bomb> bombs = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();


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

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addBomb(int positionX, int positionY, int bombRadius) {
        bombs.add(new Bomb(positionX, positionY, bombRadius));
    }

    public void addBomb(Bomb bomb) {
        this.bombs.add(bomb);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public ArrayList<MapObject> getMap() {
        return map;
    }

    public void draw(SpriteBatch batch) {
        for (MapObject obj : map) obj.draw(batch);
        for (MapObject obj : bombs) obj.draw(batch);
        for (MapObject obj : players) obj.draw(batch);
    }

    public boolean wallCheck(int x, int y) {
        for (MapObject object : map) {
            if (object.getPositionX() == x && object.getPositionY() == y) {
                return !object.getTransparent();
            }
        }
        return false;
    }

    public boolean bombCheck(int x, int y) {
        for (MapObject object : bombs) {
            if (object.getPositionX() == x && object.getPositionY() == y) {
                return true;
            }
        }
        return false;
    }

}