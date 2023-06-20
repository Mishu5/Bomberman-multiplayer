package com.bomberman.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.bomberman.common.utils.GraphicUtils.CURSOR;

public class Bomberman extends Game {
    private Screen frontScreen;

    @Override
    public void create() {
        Menu menu = new Menu(this);
        frontScreen = menu;
        setScreen(menu);
        Pixmap pixmap = new Pixmap(Gdx.files.internal(CURSOR));
        int xHotspot = 15, yHotspot = 15;
        Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        pixmap.dispose();
        Gdx.graphics.setCursor(cursor);
    }

    @Override
    public void render() {
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

    public void startGame() {
        GameArea gameArea = new GameArea(this);
        frontScreen = gameArea;
        setScreen(gameArea);
    }
}