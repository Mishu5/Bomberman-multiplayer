package com.bomberman.common.engine;

import com.bomberman.common.model.*;
import com.bomberman.common.utils.Pair;

import java.util.ArrayList;

import static com.bomberman.common.utils.EngineUtils.*;
import static com.bomberman.common.utils.EngineUtils.Direction.*;

public class GameServices {
    private final ArrayList<BombHandler> bombHandlers;
    private final ArrayList<PlayerHandler> playerHandlers;
    private final ArrayList<ClientHandler> clientHandlers;
    private final ArrayList<Destruction> destructions;
    private final Map gameEnvironment;
    private final EventListener mainListener;

    public GameServices(Map map) {
        this.gameEnvironment = map;
        playerHandlers = new ArrayList<>();
        bombHandlers = new ArrayList<>();
        clientHandlers = new ArrayList<>();
        destructions = new ArrayList<>();
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
        PlayerHandler tempPlayerHandlerHolder = new PlayerHandler(player, mainListener);
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

    synchronized public void createBomb(int x, int y, int radius) {
        for(Bomb bomb: gameEnvironment.getBombs()) {
            if(bomb.positionMatch(x, y)) return;
        }
        addBomb(new Bomb(x, y, radius));
    }


    synchronized public void detonateBomb(int x, int y, int radius) {
        ArrayList<PlayerHandler> killedHandlers = new ArrayList<>();
        ArrayList<MapObject> deletedObjects = new ArrayList<>();
        ArrayList<MapObject> addedObjects = new ArrayList<>();

        Destruction destruction = new Destruction(x, y, radius);
        for (int i = 0; i < gameEnvironment.getMap().size(); i++) {
            MapObject mo = gameEnvironment.getMap().get(i);
            if(mo.isDestructible() || mo.isTransparent()) continue;
            if(mo.getPositionX() == x) {
                if(mo.getPositionY() < destruction.getTop().second && mo.getPositionY() > y)
                    destruction.setTop(new Pair(x, mo.getPositionY() - 1));
                if(mo.getPositionY() > destruction.getBottom().second && mo.getPositionY() < y)
                    destruction.setBottom(new Pair(x, mo.getPositionY() + 1));
            }
            if(mo.getPositionY() == y) {
                if(mo.getPositionX() < destruction.getRight().first && mo.getPositionX() > x)
                    destruction.setRight(new Pair(mo.getPositionX() - 1, y));
                if(mo.getPositionX() > destruction.getLeft().first && mo.getPositionX() < x)
                    destruction.setLeft(new Pair(mo.getPositionX() + 1, y));
            }
        }

        for (PlayerHandler ph : playerHandlers) {
            if((ph.getX() == x
                    && ph.getY() <= destruction.getTop().second
                    && ph.getY() >= destruction.getBottom().second
                    ) || (ph.getY() == y
                    && ph.getX() <= destruction.getRight().first
                    && ph.getX() <= destruction.getLeft().first
            )) {
                gameEnvironment.getPlayers().removeIf(it -> it.getPlayerID() == ph.getID());
                killedHandlers.add(ph);
            }
        }

        for (MapObject mo : gameEnvironment.getMap()) {
            if (!mo.isDestructible()) continue;
            if((mo.getPositionX() == x
                    && mo.getPositionY() <= destruction.getTop().second
                    && mo.getPositionY() >= destruction.getBottom().second
            ) || (mo.getPositionY() == y
                    && mo.getPositionX() <= destruction.getRight().first
                    && mo.getPositionX() >= destruction.getLeft().first
            )) {
                addedObjects.add(new Floor(mo.getPositionX(), mo.getPositionY()));
                deletedObjects.add(mo);
            }
        }

        playerHandlers.removeAll(killedHandlers);
        gameEnvironment.getMap().removeAll(deletedObjects);
        gameEnvironment.getMap().addAll(addedObjects);
        destructions.add(destruction);

        removeBomb(x, y);
    }

    public void serviceController(int id) {
        for (PlayerHandler ph : playerHandlers) {
            if (ph.getID() == id) ph.serviceController();

        }
    }

    void playerMove(int x, int y, int id, Direction direction) {
        PlayerHandler temp = getPlayerHandler(id);
        if(temp == null) return;
        for (Bomb bomb: gameEnvironment.getBombs()) {
            if (bomb.positionMatch(x, y))
                moveBomb(x, y, direction);
        }
        if (!gameEnvironment.collisionCheck(x, y))
            temp.move(x, y);
    }

    synchronized public Map getMap(){
        return gameEnvironment;
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
            if (gameEnvironment.collisionCheck(x, y + 1)) {
                return;
            }
        } else if (direction == BOT) {
            if (gameEnvironment.collisionCheck(x, y - 1)) {
                return;
            }
        } else if (direction == RIGHT) {
            if (gameEnvironment.collisionCheck(x + 1, y)) {
                return;
            }
        } else if (direction == LEFT) {
            if (gameEnvironment.collisionCheck(x - 1, y)) {
                return;
            }
        }

        myHandler.moveBomb(direction);
    }

}
