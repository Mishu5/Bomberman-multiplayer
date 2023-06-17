package com.bomberman.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.model.Map;

public class ClientServerPackageReceiverThread extends Thread {

    private ObjectInputStream in;
    private Map map;
    private int playerId;
    private final AtomicBoolean isReceiverRunning;

    public ClientServerPackageReceiverThread(Map map, ObjectInputStream in) {
        this.map = map;
        this.in = in;
        isReceiverRunning = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        MapDTO receivedPackage = null;
        while (isReceiverRunning.get()) {

            try {
                receivedPackage = (MapDTO) in.readObject();
                copyPackageToMap(receivedPackage);
            } catch (ClassNotFoundException | IOException | InterruptedException e) {
                isReceiverRunning.set(false);
                break;
            }

        }
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

    public boolean isRunning() {
        return isReceiverRunning.get();
    }
}
