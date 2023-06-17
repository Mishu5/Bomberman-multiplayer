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
        MapDTO receivedPackage;
        while (isReceiverRunning.get()) {
            try {
                receivedPackage = (MapDTO) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                isReceiverRunning.set(false);
                break;
            }
            copyPackageToMap(receivedPackage);
        }
    }

    private void copyPackageToMap(MapDTO toCopy) {
        this.map = new Map();
        map.getPlayers().addAll(toCopy.getPlayers());
        map.getBombs().addAll(toCopy.getBombs());
        map.getMap().addAll(toCopy.getMap());
        map.setTime(toCopy.getCurrentGameTime());
        map.setGameStatus(toCopy.isGameStarted());
        playerId = toCopy.getPlayerId();
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isRunning() {
        return isReceiverRunning.get();
    }
}
