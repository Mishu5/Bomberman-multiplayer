package com.bomberman.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.bomberman.common.utils.ClientServerCommunicationUtils.*;

public class PlayerController {
    ClientServices clientServices;

    public PlayerController(ClientServices clientServices) {
        this.clientServices = clientServices;
    }

    public void serviceController() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            clientServices.post(communicationDictionary.get(Token.UP));
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            clientServices.post(communicationDictionary.get(Token.DOWN));
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            clientServices.post(communicationDictionary.get(Token.LEFT));
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            clientServices.post(communicationDictionary.get(Token.RIGHT));
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            clientServices.post(communicationDictionary.get(Token.BOMB));
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            clientServices.post(communicationDictionary.get(Token.START_GAME));
    }
}
