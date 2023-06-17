package com.bomberman.common.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bomberman.common.events.BombCreateEvent;
import com.bomberman.common.events.PlayerMoveEvent;
import com.bomberman.common.model.Player;

import static com.bomberman.common.utils.EngineUtils.*;
import static com.bomberman.common.utils.EngineUtils.Direction.*;
import static com.bomberman.common.utils.EngineUtils.PLAYER_SPEED;

public class PlayerHandler {

    private boolean radiusBoost;
    private final Player player;
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
        listener.notify(new PlayerMoveEvent(getX() + dx, getY() + dy, getID(), direction));
    }

    public void putBombAttempt() {
        int bombRadius = radiusBoost ? 2 * DETONATION_RADIUS : DETONATION_RADIUS;
        listener.notify(new BombCreateEvent(getX(), getY(), bombRadius));
    }

    public int getID() {
        return player.getPlayerID();
    }

    public int getX() {
        return player.getPositionX();
    }

    public int getY() {
        return player.getPositionY();
    }

    public void setRadiusBoost(boolean rb) { this.radiusBoost = rb; }

    public void startGameAttempt(){
        
    }

    private int getPlayerId(){
        return this.player.getPlayerID();
    }
}
