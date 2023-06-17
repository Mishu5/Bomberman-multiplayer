package com.bomberman.common.serialization;

import com.bomberman.common.model.Bomb;
import com.bomberman.common.model.MapObject;
import com.bomberman.common.model.Player;
import com.bomberman.common.model.Map;

import java.io.Serializable;
import java.util.ArrayList;

public class MapDTO implements Serializable {

    private ArrayList<MapObject> map;
    private ArrayList<Bomb> bombs;
    private ArrayList<Player> players;
    private double currentGameTime;
    private boolean gameStarted;

    private int packageID;

    private int playerId;

    public MapDTO(int packageID) {
        map = new ArrayList<>();
        bombs = new ArrayList<>();
        players = new ArrayList<>();
        this.packageID = packageID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void copy(Map map) {
        players.addAll(map.getPlayers());
        this.map.addAll(map.getMap());
        bombs.addAll(map.getBombs());
        currentGameTime = map.getGameTime();
        gameStarted = map.getGameStarted();
    }

    public ArrayList<MapObject> getMap() {
        return map;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public double getCurrentGameTime() {
        return currentGameTime;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setPlayerId(int id) {
        this.playerId = id;
    }

    public int getPlayerId() {
        return playerId;
    }

}
