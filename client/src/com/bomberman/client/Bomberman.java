package com.bomberman.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

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
        frontScreen = gameArea;
        setScreen(gameArea);
    }
}