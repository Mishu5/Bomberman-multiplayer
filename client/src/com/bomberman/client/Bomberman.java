package com.bomberman.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.bomberman.client.gui.GameArea;
import com.bomberman.client.gui.GameView;
import com.bomberman.client.gui.InfoMessage;
import com.bomberman.client.gui.Menu;
import com.bomberman.common.utils.EngineUtils;

import static com.bomberman.common.utils.GraphicUtils.CURSOR;

public class Bomberman extends Game {
    private GameView frontScreen;
    private GameView backgroundScreen;

    @Override
    public void create() {
        runMenu();
        Pixmap pixmap = new Pixmap(Gdx.files.internal(CURSOR));
        int xHotspot = 15, yHotspot = 15;
        Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        pixmap.dispose();
        Gdx.graphics.setCursor(cursor);
    }

    @Override
    public void render() {
        EngineUtils.GameState gameState = frontScreen.getGameState();
        switch (gameState) {
            case WIN:
                infoMessage(gameState, "You won the game!");
                break;
            case LOSS:
                infoMessage(gameState, "You lost the game!");
                break;
            case DISCONNECTED:
                infoMessage(gameState, "You have been disconnected.");
                break;
            case OFFLINE_NOTIFICATION:
                offlineMode();
            default:
                break;
        }
        frontScreen.render(0f);
    }

    @Override
    public void dispose() {
        frontScreen.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        frontScreen.resize(width, height);
    }

    public void runMenu() {
        Menu menu = new Menu(this);
        if(frontScreen != null) frontScreen.dispose();
        frontScreen = menu;
        setScreen(menu);
    }

    public void startGame() {
        GameArea gameArea = new GameArea(this);
        frontScreen.dispose();
        frontScreen = gameArea;
        setScreen(gameArea);
    }

    public void resumeGame() {
        frontScreen.dispose();
        frontScreen = backgroundScreen;
        setScreen(frontScreen);
        frontScreen.resume();
    }

    public void disconnectGame() {
        InfoMessage infoMessage = new InfoMessage(
                this,
                EngineUtils.GameState.DISCONNECTED,
                "Client has been disconnected."
        );
        if(frontScreen != null) frontScreen.dispose();
        frontScreen = infoMessage;
        setScreen(infoMessage);
    }

    public void offlineMode() {
        InfoMessage infoMessage = new InfoMessage(
                this,
                EngineUtils.GameState.IDLE,
                "Server is unavailable.\nGame will start offline."
        );
        backgroundScreen = frontScreen;
        frontScreen = infoMessage;
        setScreen(infoMessage);
    }

    public void infoMessage(EngineUtils.GameState state, String message) {
        InfoMessage infoMessage = new InfoMessage(
                this,
                state,
                message
        );
        if(frontScreen != null) frontScreen.dispose();
        frontScreen = infoMessage;
        setScreen(infoMessage);
    }
}