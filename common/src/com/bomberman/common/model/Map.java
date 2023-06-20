package com.bomberman.common.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static com.bomberman.common.utils.EngineUtils.DETONATION_TIME;
import static com.bomberman.common.utils.GraphicUtils.DESTRUCTION_ANIMATION_TIME;
import static java.lang.Thread.sleep;

public class Map {
    private final Semaphore semaphore;
    private ArrayList<MapObject> map;
    private ArrayList<Bomb> bombs;
    private ArrayList<Player> players;
    private ArrayList<Destruction> destructions;
    private double gameTime;
    private boolean gameStarted;

    public Map() {
        semaphore = new Semaphore(1);
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

    public void addTime(double time) {
        this.gameTime += time;
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

    public void addSpawn(int positionX, int positionY, int spawnID) {
        map.add(new Spawn(positionX, positionY, spawnID));
    }

    public Spawn getSpawn(int spawnID) {

        Spawn spawn = null;

        for (MapObject object : map) {
            if (object instanceof Spawn) {
                spawn = (Spawn) object;
                if (spawn.getSpawnID() - 1 == spawnID) {
                    break;
                }
            }
        }

        return spawn;
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

    public ArrayList<Destruction> getDestructions() {
        return destructions;
    }

    public void setMap(ArrayList<MapObject> map) {
        this.map = map;
    }

    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setDestructions(ArrayList<Destruction> destructions) {
        this.destructions = destructions;
    }

    synchronized public void draw(SpriteBatch batch) throws InterruptedException {
        semaphore.acquire();
        for (MapObject obj : map) obj.draw(batch);
        serviceAnimations(batch);
        for (MapObject obj : players) obj.draw(batch);
        for (MapObject obj : bombs) obj.draw(batch);
        semaphore.release();
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
        //Destruction animation
        destructions.forEach((it) -> {
            if (it.isAnimationStarted()) it.draw(batch);
            else {
                Thread animationThread = new Thread(() -> {
                    float delta = 0.1f;
                    float frame = 0f;
                    try {
                        while (frame < DESTRUCTION_ANIMATION_TIME) {
                            sleep((long) (delta * 1000));
                            frame += delta;
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