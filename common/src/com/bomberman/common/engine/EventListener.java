package com.bomberman.common.engine;

import com.bomberman.common.events.BombDetonateEvent;
import com.bomberman.common.events.BombMoveEvent;
import com.bomberman.common.events.PlayerDisconnectEvent;
import com.bomberman.common.events.PlayerMoveEvent;

import java.util.ArrayList;

import static com.bomberman.common.utils.EngineUtils.EVENT_SERVICE_DELAY;

public class EventListener {
    final private GameServices services;
    private final ArrayList<BombDetonateEvent> bombDetonateEvents;
    private final ArrayList<PlayerDisconnectEvent> playerDisconnectEvents;
    private final ArrayList<PlayerMoveEvent> playerMoveEvents;

    private final ArrayList<BombMoveEvent> bombMoveEvents;

    public EventListener(GameServices services) {
        this.services = services;
        bombDetonateEvents = new ArrayList<>();
        playerDisconnectEvents = new ArrayList<>();
        playerMoveEvents = new ArrayList<>();
        bombMoveEvents = new ArrayList<>();
    }

    public void startListening() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(EVENT_SERVICE_DELAY);
                    if (!playerDisconnectEvents.isEmpty()) {
                        serviceEvent(playerDisconnectEvents.get(0));
                        playerDisconnectEvents.remove(0);
                    }
                    if (!playerMoveEvents.isEmpty()) {
                        serviceEvent(playerMoveEvents.get(0));
                        playerMoveEvents.remove(0);
                    }
                    if (!bombDetonateEvents.isEmpty()) {
                        serviceEvent(bombDetonateEvents.get(0));
                        bombDetonateEvents.remove(0);
                    }
                    if (!bombMoveEvents.isEmpty()) {
                        serviceEvent(bombMoveEvents.get(0));
                        bombMoveEvents.remove(0);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public void notify(BombDetonateEvent bde) {
        bombDetonateEvents.add(bde);
    }

    public void serviceEvent(BombDetonateEvent bde) {
        services.detonateBomb(bde.getPosX(), bde.getPosY(), bde.getRadius());
    }

    public void notify(BombMoveEvent bme) {
        bombMoveEvents.add(bme);
    }

    public void serviceEvent(BombMoveEvent bme) {
        services.moveBomb(bme.getPosX(), bme.getPosY(), bme.getDirection());
    }

    public void notify(PlayerMoveEvent pme) {
        playerMoveEvents.add(pme);
    }

    public void serviceEvent(PlayerMoveEvent pme) {
        services.playerMove(
                pme.getPosX(),
                pme.getPosY(),
                pme.getPlayerID(),
                pme.getDirection()
        );
    }

    public void notify(PlayerDisconnectEvent pde) {
        playerDisconnectEvents.add(pde);
    }

    public void serviceEvent(PlayerDisconnectEvent pde) {
        services.removePlayers(pde.getPlayerID());
    }
}
