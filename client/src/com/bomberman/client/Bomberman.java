package com.bomberman.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

import static com.bomberman.common.utils.GraphicUtils.CURSOR;

public class Bomberman extends Game {
    private Screen frontScreen;

    @Override
    public void create() {
        Menu menu = new Menu(this);
        frontScreen = menu;
        setScreen(menu);
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
        frontScreen.dispose();
        frontScreen = gameArea;
        setScreen(gameArea);
    }
}