package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bomberman.common.utils.GraphicUtils;

import java.util.ArrayList;

import static com.bomberman.common.utils.GraphicUtils.DESTRUCTION_ANIMATION_TIME;
import static java.lang.Thread.sleep;

public class Map {
    private final ArrayList<MapObject> map;
    private final ArrayList<Bomb> bombs;
    private final ArrayList<Player> players;
    private final ArrayList<Destruction> destructions;

    private double gameTime;

    private boolean gameStarted;

    public Map() {
        map = new ArrayList<>();
        bombs = new ArrayList<>();
        players = new ArrayList<>();
        destructions = new ArrayList<>();
        gameTime = 0;
        gameStarted = false;
    }

    public boolean getGameStarted() {
        return gameStarted;
    }

    public void setGameStatus(boolean status) {
        this.gameStarted = status;
    }

    public double getGameTime() {
        return gameTime;
    }

    public void setTime(double time) {
        this.gameTime = time;
    }

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

    public void addAnimation(Destruction destruction) {
        destructions.add(destruction);
    }
    public void addPlayer(int positionX, int positionY, int playerID) {
        players.add(new Player(positionX, positionY, playerID));
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addBomb(int positionX, int positionY, int bombRadius, int timer) {
        bombs.add(new Bomb(positionX, positionY, bombRadius, timer));
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
        serviceAnimations(batch);
        for (MapObject obj : players) obj.draw(batch);
    }

    public boolean collisionCheck(int x, int y) {
        for (MapObject object : map) {
            if (object.getPositionX() == x && object.getPositionY() == y) {
                if (!object.isTransparent()) return true;
                break;
            }
        }
        for (Bomb bomb : bombs) {
            if (bomb.getPositionX() == x && bomb.getPositionY() == y) {
                return true;
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

    public void serviceAnimations(SpriteBatch batch) {
        destructions.forEach((it) -> {
            if(it.isAnimationStarted()) it.draw(batch);
            else {
                Thread animationThread = new Thread(() -> {
                    float animationFrame = 0.1f;
                    float counter = 0f;
                    try {
                        while(counter < DESTRUCTION_ANIMATION_TIME) {
                            sleep((long) (animationFrame * 1000));
                            counter += animationFrame;
                        }
                        destructions.remove(it);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                animationThread.start();
                it.animationStart();
            }
        });
    }
}