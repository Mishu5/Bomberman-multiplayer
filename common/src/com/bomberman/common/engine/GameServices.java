package com.bomberman.common.engine;

import com.bomberman.common.model.*;
import jdk.internal.net.http.common.Pair;

import java.util.ArrayList;

import static com.bomberman.common.engine.PlayerHandler.Direction.*;
import static com.bomberman.common.engine.PlayerHandler.Direction.LEFT;

public class GameServices {
    private final ArrayList<BombHandler> bombHandlers;
    private final ArrayList<PlayerHandler> playerHandlers;
    private final Map gameEnvironment;
    private final EventListener mainListener;

    public GameServices(Map map) {
        this.gameEnvironment = map;
        playerHandlers = new ArrayList<>();
        bombHandlers = new ArrayList<>();
        mainListener = new EventListener(this);
        mainListener.startListening();
    }

    public void addPlayer(Player player) {
        this.gameEnvironment.addPlayer(player);
        this.playerHandlers.add(new PlayerHandler(player, mainListener, gameEnvironment));
    }

    public void removePlayers(int id) {
        playerHandlers.removeIf(it -> it.getID() == id);
        gameEnvironment.getPlayers().removeIf(it -> it.getPlayerID() == id);
    }

    public void addBomb(Bomb bomb) {
        this.gameEnvironment.addBomb(bomb);
        BombHandler bh = new BombHandler(bomb, mainListener);
        this.bombHandlers.add(bh);
        bh.serviceBomb();
    }

    public void removeBomb(int x, int y) {
        bombHandlers.removeIf(it -> it.positionMatch(x, y));
        gameEnvironment.getBombs().removeIf(it -> it.positionMatch(x, y));
    }

    public boolean moveBomb(int x, int y, PlayerHandler.Direction direction) {
        BombHandler myHandler = null;
        for (BombHandler bh : bombHandlers) {
            if (bh.positionMatch(x, y)) {
                myHandler = bh;
                break;
            }
        }


        if (direction == TOP) {
            if (gameEnvironment.wallCheck(x, y + 1) || gameEnvironment.bombCheck(x, y + 1)) {
                return false;
            }
        } else if (direction == BOT) {
            if (gameEnvironment.wallCheck(x, y - 1) || gameEnvironment.bombCheck(x, y - 1)) {
                return false;
            }
        } else if (direction == RIGHT) {
            if (gameEnvironment.wallCheck(x + 1, y) || gameEnvironment.bombCheck(x + 1, y)) {
                return false;
            }
        } else if (direction == LEFT) {
            if (gameEnvironment.wallCheck(x - 1, y) || gameEnvironment.bombCheck(x - 1, y)) {
                return false;
            }
        }


        if (myHandler == null) {
            return false;
        }
        myHandler.moveBomb(direction);


        return true;
    }

    synchronized public void detonateBomb(int x, int y, int radius) {
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
        ArrayList<MapObject> addedObjects = new ArrayList<>();
        for (int i = 0; i < gameEnvironment.getMap().size(); i++) {
            MapObject mo = gameEnvironment.getMap().get(i);
            if (!mo.isDestructible()) continue;
            if (mo.getPositionX() >= x - radius && mo.getPositionX() <= x + radius
                    && mo.getPositionY() >= y - radius && mo.getPositionY() <= y + radius) {
                addedObjects.add(new Floor(mo.getPositionX(), mo.getPositionY()));
                deletedObjects.add(mo);
            }
        }
        gameEnvironment.getMap().removeAll(deletedObjects);
        gameEnvironment.getMap().addAll(addedObjects);

        removeBomb(x, y);
    }

    public void serviceController(int id) {
        for (PlayerHandler ph : playerHandlers) {
            if (ph.getID() == id) ph.serviceController();

        }
    }
}
