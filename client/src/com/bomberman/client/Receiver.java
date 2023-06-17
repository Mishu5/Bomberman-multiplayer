package com.bomberman.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.model.Map;
import com.bomberman.common.utils.EngineUtils;

import static com.bomberman.common.utils.ClientServerCommunicationUtils.CLIENT_RECEIVER_DELAY;

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
                sleep(CLIENT_RECEIVER_DELAY);
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
        map.setMap(toCopy.getMap());
        map.setBombs(toCopy.getBombs());
        map.setPlayers(toCopy.getPlayers());
        map.setDestructions(toCopy.getDestructionEvents());
        map.setTime(toCopy.getCurrentGameTime());
        map.setGameStatus(toCopy.isGameStarted());
        playerId = toCopy.getPlayerId();
    }

    public int getPlayerId() {
        return playerId;
    }

    synchronized public boolean isRunning() {
        return isRunning.get();
    }
}