package Shared;

import java.util.ArrayList;

public class Map {

    ArrayList<MapObject> map = new ArrayList<MapObject>();

    public void addDestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, true));
    }

    public void addIndestructibleWall(int positionX, int positionY) {
        map.add(new Wall(positionX, positionY, false));
    }

    public void addPowerUP(int positionX, int positionY) {
        map.add(new PowerUP(positionX, positionY));
    }

    public void addFloor(int positionX, int positionY) {
        map.add(new Floor(positionX, positionY));
    }

    public void addPlayer(int positionX, int positionY, int playerID) {
        map.add(new Player(positionX, positionY, playerID));
    }

    public void addBomb(int positionX, int positionY) {
        map.add(new Bomb(positionX, positionY));
    }

}
