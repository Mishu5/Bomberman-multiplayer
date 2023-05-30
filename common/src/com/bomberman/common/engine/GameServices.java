package com.bomberman.common.engine;

import com.bomberman.common.model.Bomb;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.MapObject;
import com.bomberman.common.model.Player;

import java.util.ArrayList;

public class GameServices {
    private ArrayList<BombHandler> bombHandlers;
    private ArrayList<PlayerHandler> playerHandlers;
    private Map gameEnvironment;

    public GameServices(Map map) {
        this.gameEnvironment = map;
        playerHandlers = new ArrayList<>();
        bombHandlers = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.gameEnvironment.addPlayer(player);
        this.playerHandlers.add(new PlayerHandler(player, new EventListener(this)));
    }

    public void removePlayers(int id) {
        playerHandlers.removeIf(it -> it.getID() == id);
        gameEnvironment.getPlayers().removeIf(it -> it.getPlayerID() == id);
    }

    public void addBomb(Bomb bomb) {
        this.gameEnvironment.addBomb(bomb);
        BombHandler bh = new BombHandler(bomb, new EventListener(this));
        this.bombHandlers.add(bh);
        bh.serviceBomb();
    }

    public void removeBomb(int x, int y) {
        bombHandlers.removeIf(it -> it.positionMatch(x, y));
        gameEnvironment.getBombs().removeIf(it -> it.positionMatch(x, y));
    }

    public void detonateBomb(int x, int y, int radius) {
        ArrayList<PlayerHandler> killedHandlers = new ArrayList<>();
        for (PlayerHandler ph : playerHandlers) {
            if (ph.getX() >= x - radius && ph.getX() <= x + radius &&
                    ph.getY() >= y - radius && ph.getY() <= y + radius) {
                gameEnvironment.getPlayers().removeIf(it -> it.getPlayerID() == ph.getID());
                killedHandlers.add(ph);
            }
        }
        playerHandlers.removeAll(killedHandlers);

        ArrayList<MapObject> deletedObjects = new ArrayList<>();
        for(int i = 0 ; i < gameEnvironment.getMap().size() ; i++) {
            MapObject mo = gameEnvironment.getMap().get(i);
            if(!mo.isDestructible()) continue;
            if(mo.getPositionX() >= x - radius && mo.getPositionX() <= x + radius
                    && mo.getPositionY() >= y + radius && mo.getPositionY() <= y - radius) {
                gameEnvironment.addFloor(mo.getPositionX(), mo.getPositionY());
                deletedObjects.add(mo);
            }
        }
        gameEnvironment.getMap().removeAll(deletedObjects);

        removeBomb(x, y);
    }

    public void serviceController(int id) {
        for(PlayerHandler ph: playerHandlers) {
            if(ph.getID() == id) ph.serviceController();
        }
    }
}
