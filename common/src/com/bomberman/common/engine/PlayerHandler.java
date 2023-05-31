package com.bomberman.common.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bomberman.common.events.BombMoveEvent;
import com.bomberman.common.model.Bomb;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.Parser;

import static com.bomberman.common.engine.PlayerHandler.Direction.*;
import static com.bomberman.common.utils.EngineUtils.PLAYER_SPEED;

public class PlayerHandler {
    public enum Direction {TOP, BOT, LEFT, RIGHT}

    private Player player;
    private Map map;
    private EventListener listener;

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
            characterMove(0, PLAYER_SPEED, TOP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            characterMove(0, -PLAYER_SPEED, BOT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            characterMove(-PLAYER_SPEED, 0, LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            characterMove(PLAYER_SPEED, 0, RIGHT);
    }

    public void characterMove(int x, int y, Direction direction) {
        int playerNewPositionX = player.getPositionX();
        int playerNewPositionY = player.getPositionY();


        for (Bomb bomb : map.getBombs()) {
            if (bomb.positionMatch(playerNewPositionX + x, playerNewPositionY + y)) {


                if (listener.serviceEvent(new BombMoveEvent(playerNewPositionX + x, playerNewPositionY + y, direction))) {
                    break;
                } else {
                    return;
                }
            }
        }
        if(!validate_move(x,y))
            player.move(x,y);
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
