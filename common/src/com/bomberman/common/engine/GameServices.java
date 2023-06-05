package com.bomberman.common.engine;

import com.bomberman.common.events.BombMoveEvent;
import com.bomberman.common.model.*;

import java.util.ArrayList;

import static com.bomberman.common.utils.EngineUtils.*;
import static com.bomberman.common.utils.EngineUtils.Direction.*;

public class GameServices {
    private final ArrayList<BombHandler> bombHandlers;
    private final ArrayList<PlayerHandler> playerHandlers;
    private final ArrayList<ClientHandler> clientHandlers;
    private final Map gameEnvironment;
    private final EventListener mainListener;

    public GameServices(Map map) {
        this.gameEnvironment = map;
        playerHandlers = new ArrayList<>();
        bombHandlers = new ArrayList<>();
        clientHandlers = new ArrayList<>();
        mainListener = new EventListener(this);
        mainListener.startListening();
    }

    public PlayerHandler getPlayerHandler(int id) {
        for (PlayerHandler ph : playerHandlers)
            if (ph.getID() == id) return ph;
        return null;
    }

    public PlayerHandler addPlayer(Player player) {
        this.gameEnvironment.addPlayer(player);
        PlayerHandler tempPlayerHandlerHolder = new PlayerHandler(player, mainListener, gameEnvironment);
        this.playerHandlers.add(tempPlayerHandlerHolder);
        return tempPlayerHandlerHolder;
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

    public void moveBomb(int x, int y, Direction direction) {
        BombHandler myHandler = null;
        for (BombHandler bh : bombHandlers) {
            if (bh.positionMatch(x, y)) {
                myHandler = bh;
                break;
            }
        }
        if (myHandler == null) return;

        if (direction == TOP) {
            if (gameEnvironment.wallCheck(x, y + 1) || gameEnvironment.bombCheck(x, y + 1)) {
                return;
            }
        } else if (direction == BOT) {
            if (gameEnvironment.wallCheck(x, y - 1) || gameEnvironment.bombCheck(x, y - 1)) {
                return;
            }
        } else if (direction == RIGHT) {
            if (gameEnvironment.wallCheck(x + 1, y) || gameEnvironment.bombCheck(x + 1, y)) {
                return;
            }
        } else if (direction == LEFT) {
            if (gameEnvironment.wallCheck(x - 1, y) || gameEnvironment.bombCheck(x - 1, y)) {
                return;
            }
        }

        myHandler.moveBomb(direction);
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

    void playerMove(int x, int y, int id, Direction direction) {
        PlayerHandler temp = getPlayerHandler(id);
        if (temp == null) return;
        for (Bomb bomb : gameEnvironment.getBombs()) {
            if (bomb.positionMatch(x, y))
                mainListener.serviceEvent(new BombMoveEvent(x, y, direction));
        }
        if (!gameEnvironment.wallCheck(x, y))
            temp.move(x, y);
    }

    synchronized public Map getMap(){
        return gameEnvironment;
    }
}
