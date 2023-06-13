package com.bomberman.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.model.Map;

public class ClientServerPackageReceiverThread extends Thread {

    private ObjectInputStream in;
    private Map map;
    private int playerId;

    public ClientServerPackageReceiverThread(Map map, ObjectInputStream in) {
        this.map = map;
        this.in = in;
    }

    public void run() {

        MapDTO receivedPackage = null;

        while (true) {

            try {
                receivedPackage = (MapDTO) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                break;
            }

            copyPackageToMap(receivedPackage);

        }

    }

    private void copyPackageToMap(MapDTO toCopy) {

        while (!toCopy.getPlayers().isEmpty()) {
            map.getPlayers().add(toCopy.getPlayers().get(0));
            toCopy.getPlayers().remove(0);
        }

        while (!toCopy.getMap().isEmpty()) {
            map.getMap().add(toCopy.getMap().get(0));
            toCopy.getMap().remove(0);
        }

        while (!toCopy.getBombs().isEmpty()) {
            map.getBombs().add(toCopy.getBombs().get(0));
            toCopy.getBombs().remove(0);
        }

        map.setTime(toCopy.getCurrentGameTime());
        map.setGameStatus(toCopy.isGameStarted());
        playerId = toCopy.getPlayerId();

    }

    public int getPlayerId() {
        return playerId;
    }

}
