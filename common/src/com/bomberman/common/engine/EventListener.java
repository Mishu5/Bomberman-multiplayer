package com.bomberman.common.engine;

import com.bomberman.common.events.*;

import java.util.ArrayList;

import static com.bomberman.common.utils.EngineUtils.EVENT_SERVICE_DELAY;

public class EventListener {
    final private GameServices services;
    private final ArrayList<PlayerDisconnectEvent> playerDisconnectEvents;
    private final ArrayList<PlayerMoveEvent> playerMoveEvents;
    private final ArrayList<BombCreateEvent> bombCreateEvents;
    private final ArrayList<BombDetonateEvent> bombDetonateEvents;
    private final ArrayList<BombMoveEvent> bombMoveEvents;
    private final ArrayList<StartGameEvent> startGameEvents;

    public EventListener(GameServices services) {
        this.services = services;
        playerDisconnectEvents = new ArrayList<>();
        playerMoveEvents = new ArrayList<>();
        bombCreateEvents = new ArrayList<>();
        bombDetonateEvents = new ArrayList<>();
        bombMoveEvents = new ArrayList<>();
        startGameEvents = new ArrayList<>();
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
                    if (!bombCreateEvents.isEmpty()) {
                        serviceEvent(bombCreateEvents.get(0));
                        bombCreateEvents.remove(0);
                    }
                    if (!bombDetonateEvents.isEmpty()) {
                        serviceEvent(bombDetonateEvents.get(0));
                        bombDetonateEvents.remove(0);
                    }
                    if (!bombMoveEvents.isEmpty()) {
                        serviceEvent(bombMoveEvents.get(0));
                        bombMoveEvents.remove(0);
                    }
                    if (!startGameEvents.isEmpty()) {
                        serviceEvent(startGameEvents.get(0));
                        startGameEvents.remove(0);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public void notify(BombCreateEvent bce) {
        bombCreateEvents.add(bce);
    }

    public void serviceEvent(BombCreateEvent bce) {
        services.createBomb(bce.getPosX(), bce.getPosY(), bce.getRadius());
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

    public void notify(StartGameEvent sge) {
        startGameEvents.add(sge);
    }

    public void serviceEvent(StartGameEvent sge) {
        if (sge.getPlayerID() == 0 && services.getMap().getPlayers().size() >= 2) {
            services.getMap().setGameStatus(true);
        }
    }

}
