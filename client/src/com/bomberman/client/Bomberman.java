package com.bomberman.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.bomberman.client.gui.GameArea;
import com.bomberman.client.gui.GameView;
import com.bomberman.client.gui.Menu;

import static com.bomberman.common.utils.GraphicUtils.CURSOR;

public class Bomberman extends Game {
    private GameView frontScreen;

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
        frontScreen.getGameState();
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

    private void runMenu() {
        Menu menu = new Menu(this);
        frontScreen = menu;
        setScreen(menu);
    }

    public void startGame() {
        GameArea gameArea = new GameArea(this);
        frontScreen.dispose();
        frontScreen = gameArea;
        setScreen(gameArea);
    }

    public void disconnectGame() {
        System.out.println("Client has been disconnected.");
        frontScreen.dispose();
        runMenu();
    }

    public void offlineMode() {
        System.out.println("Server is offline. Try to run server.");
    }
}