package com.bomberman.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.utils.EngineUtils;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.exit;
import static com.bomberman.common.utils.ClientServerCommunicationUtils.*;

public class PlayerController {
    ClientServices clientServices;

    public PlayerController(ClientServices clientServices) {
        this.clientServices = clientServices;
    }

    public void serviceController() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP))
            clientServices.post(communicationDictionary.get(Token.UP));
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            clientServices.post(communicationDictionary.get(Token.DOWN));
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            clientServices.post(communicationDictionary.get(Token.LEFT));
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            clientServices.post(communicationDictionary.get(Token.RIGHT));
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            clientServices.post(communicationDictionary.get(Token.BOMB));
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            clientServices.post(communicationDictionary.get(Token.START_GAME));
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
            Gdx.app.exit();
    }

    public void serviceControllerOffline(PlayerHandler playerHandler) {
        if(playerHandler == null) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            playerHandler.moveAttempt(0, 1, EngineUtils.Direction.TOP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            playerHandler.moveAttempt(0, -1, EngineUtils.Direction.BOT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            playerHandler.moveAttempt(-1, 0, EngineUtils.Direction.LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            playerHandler.moveAttempt(1, 0, EngineUtils.Direction.RIGHT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            playerHandler.putBombAttempt();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
            Gdx.app.exit();
    }
}
