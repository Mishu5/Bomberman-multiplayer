package com.bomberman.common.engine;

import com.bomberman.common.events.BombDetonateEvent;
import com.bomberman.common.events.BombMoveEvent;
import com.bomberman.common.events.PlayerDisconnectEvent;

import java.util.ArrayList;

import static com.bomberman.common.utils.EngineUtils.EVENT_SERVICE_DELAY;

public class EventListener {
    final private GameServices services;
    private final ArrayList<BombDetonateEvent> bombEvents;
    private final ArrayList<PlayerDisconnectEvent> playerDisconnectEvents;

    public EventListener(GameServices services) {
        this.services = services;
        bombEvents = new ArrayList<>();
        playerDisconnectEvents = new ArrayList<>();
    }

    public void startListening() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(EVENT_SERVICE_DELAY);
                    if (!bombEvents.isEmpty()) {
                        serviceEvent(bombEvents.get(0));
                        bombEvents.remove(0);
                    }
                    if (!bombEvents.isEmpty()) {
                        serviceEvent(bombEvents.get(0));
                        bombEvents.remove(0);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public void notify(BombDetonateEvent bde) {
        bombEvents.add(bde);
    }

    public void serviceEvent(BombDetonateEvent bde) {
        services.detonateBomb(bde.getPosX(), bde.getPosY(), bde.getRadius());
    }

    public boolean serviceEvent(BombMoveEvent bme) {
        return services.moveBomb(bme.getPosX(), bme.getPosY(), bme.getDirection());


    }

    public void notify(PlayerDisconnectEvent pde) {
        playerDisconnectEvents.add(pde);
    }

    public void serviceEvent(PlayerDisconnectEvent bde) {
        services.removePlayers(bde.getPlayerID());
    }
}
