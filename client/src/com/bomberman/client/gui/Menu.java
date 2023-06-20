package com.bomberman.client.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.client.Bomberman;
import com.bomberman.common.utils.EngineUtils;

import static com.bomberman.common.utils.GraphicUtils.*;

public class Menu implements Screen, GameView {
    private final Bomberman game;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final SpriteBatch sprite;
    private final Skin skin;
    public Menu(Bomberman game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        sprite = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(MENU);
        skin = new Skin(Gdx.files.internal(SKIN));
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        table.row().pad(15, 0, 10, 0);
        TextButton newGameBtn = new TextButton("New Game", skin);
        table.add(newGameBtn).fillX().uniformX();
        table.row().pad(25, 0, 10, 0);
        TextButton exitBtn = new TextButton("Exit", skin);
        table.add(exitBtn).fillX().uniformX();


        newGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame();
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.begin();
        sprite.draw(backgroundTexture, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }

    @Override
    public EngineUtils.GameState getGameState() {
        return EngineUtils.GameState.IDLE;
    }
}
