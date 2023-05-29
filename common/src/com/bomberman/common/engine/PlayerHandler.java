package com.bomberman.common.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bomberman.common.model.Player;

import static com.bomberman.common.utils.EngineUtils.PLAYER_SPEED;

public class PlayerHandler {
    private Player player;

    public PlayerHandler(Player player) {
        this.player = player;
    }

    public void serviceController() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            characterMove(0, PLAYER_SPEED);
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            characterMove(0, -PLAYER_SPEED);
        if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            characterMove(-PLAYER_SPEED, 0);
        if(Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            characterMove(PLAYER_SPEED, 0);
    }

    public void characterMove(int x, int y) {
        player.move(x, y);
    }
}
