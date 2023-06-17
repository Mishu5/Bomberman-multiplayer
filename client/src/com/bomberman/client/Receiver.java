package com.bomberman.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.model.Map;

public class Receiver extends Thread {

    private ObjectInputStream in;
    private Map map;
    private int playerId;
    private final AtomicBoolean isRunning;

    public Receiver(Map map, ObjectInputStream in) {
        this.map = map;
        this.in = in;
        isRunning = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            try {
                MapDTO message = (MapDTO) in.readObject();
                copyPackageToMap(message);

            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                isRunning.set(false);
                break;
            }
        }
    }

    synchronized public void stopThread() {
        isRunning.set(false);
    }

    private void copyPackageToMap(MapDTO toCopy) throws InterruptedException {
        map.getSemaphore().acquire();
        map.getPlayers().clear();
        map.getMap().clear();
        map.getBombs().clear();
        map.getPlayers().addAll(toCopy.getPlayers());
        map.getBombs().addAll(toCopy.getBombs());
        map.getMap().addAll(toCopy.getMap());
        map.setTime(toCopy.getCurrentGameTime());
        map.setGameStatus(toCopy.isGameStarted());
        playerId = toCopy.getPlayerId();
        map.getSemaphore().release();
    }

    public int getPlayerId() {
        return playerId;
    }

    synchronized public boolean isRunning() {
        return isRunning.get();
    }
}
