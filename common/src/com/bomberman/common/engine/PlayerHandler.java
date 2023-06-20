package com.bomberman.common.engine;

import com.bomberman.common.events.BombCreateEvent;
import com.bomberman.common.events.PlayerDisconnectEvent;
import com.bomberman.common.events.PlayerMoveEvent;
import com.bomberman.common.events.StartGameEvent;
import com.bomberman.common.model.Player;

import static com.bomberman.common.utils.EngineUtils.*;

public class PlayerHandler {

    private boolean radiusBoost;
    private Player player;
    private final EventListener listener;

    public PlayerHandler(Player player, EventListener listener) {
        this.player = player;
        this.listener = listener;
        radiusBoost = false;
    }

    public void move(int x, int y) {
        player.move(x, y);
    }

    public void moveAttempt(int dx, int dy, Direction direction) {
        if (player == null) return;
        listener.notify(new PlayerMoveEvent(getX() + dx, getY() + dy, getID(), direction));
    }

    public void putBombAttempt() {
        if (player == null) return;
        int bombRadius = radiusBoost ? 2 * DETONATION_RADIUS : DETONATION_RADIUS;
        listener.notify(new BombCreateEvent(getX(), getY(), bombRadius));
    }

    public int getID() {
        if (player == null) return 0;
        return player.getPlayerID();
    }

    public int getX() {
        return player.getPositionX();
    }

    public int getY() {
        return player.getPositionY();
    }

    public void setRadiusBoost(boolean rb) {
        this.radiusBoost = rb;
    }

    public void startGameAttempt() {
        if (player == null) return;
        listener.notify(new StartGameEvent(getPlayerId()));
    }

    private int getPlayerId() {
        return this.player.getPlayerID();
    }

    public Player getPlayer() {
        return player;
    }

    public void killPlayer() {
        this.player = null;
    }

    public void disconnected(){
        listener.notify(new PlayerDisconnectEvent(getPlayerId()));
    }
}

