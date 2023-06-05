package com.bomberman.common.serialization;

import com.bomberman.common.model.Bomb;
import com.bomberman.common.model.MapObject;
import com.bomberman.common.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class MapDTO implements Serializable {
    private final ArrayList<MapObject> map;
    private final ArrayList<Bomb> bombs;
    private final ArrayList<Player> players;

    public MapDTO (
            ArrayList<MapObject> map,
            ArrayList<Bomb> bombs,
            ArrayList<Player> players
    ) {
        this.map = map;
        this.bombs = bombs;
        this.players = players;
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
}