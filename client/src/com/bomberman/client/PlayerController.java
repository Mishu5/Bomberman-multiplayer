package com.bomberman.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bomberman.common.utils.ClientServerCommunicationUtils;

import static com.bomberman.common.utils.ClientServerCommunicationUtils.*;
import static com.bomberman.common.utils.EngineUtils.Direction.*;
import static com.bomberman.common.utils.EngineUtils.PLAYER_SPEED;

public class PlayerController {
    ClientServices clientServices;

    public PlayerController(ClientServices clientServices) {
        this.clientServices = clientServices;
    }

    public void serviceController() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            clientServices.sentInput(communicationDictionary.get(Token.UP));
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            clientServices.sentInput(communicationDictionary.get(Token.DOWN));
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            clientServices.sentInput(communicationDictionary.get(Token.LEFT));
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            clientServices.sentInput(communicationDictionary.get(Token.RIGHT));
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            clientServices.sentInput(communicationDictionary.get(Token.BOMB));
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            clientServices.sentInput(communicationDictionary.get(Token.START_GAME));
    }
}
