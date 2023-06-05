package com.bomberman.common.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bomberman.common.events.PlayerMoveEvent;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;

import static com.bomberman.common.utils.EngineUtils.*;
import static com.bomberman.common.utils.EngineUtils.Direction.*;
import static com.bomberman.common.utils.EngineUtils.PLAYER_SPEED;

public class PlayerHandler {

    private final Player player;
    private final Map map;
    private final EventListener listener;

    public PlayerHandler(Player player, EventListener listener, Map map) {
        this.player = player;
        this.map = map;
        this.listener = listener;
    }

    private boolean validate_move(int x, int y){
        return (map.wallCheck(player.getPositionX() + x, player.getPositionY() + y));
    }

    public void serviceController() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            moveAttempt(0, PLAYER_SPEED, TOP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveAttempt(0, -PLAYER_SPEED, BOT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveAttempt(-PLAYER_SPEED, 0, LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveAttempt(PLAYER_SPEED, 0, RIGHT);
    }

    public void moveAttempt(int x, int y, Direction direction) {
        listener.serviceEvent(new PlayerMoveEvent(getX() + x, getY() + y, getID(), direction));
    }

    public void move(int x, int y) {
        player.move(x, y);
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
}
