package com.bomberman.client.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;

import java.util.ArrayList;

import static com.bomberman.common.utils.GraphicUtils.*;

public class SidePanel implements Screen {
    private final ScreenViewport sidebarViewport;
    private final Camera camera;
    private final Stage stage;
    private ArrayList<Player> players;
    private final Texture backgroundTexture;

    SidePanel(Map map) {
        camera = new PerspectiveCamera();
        sidebarViewport = new ScreenViewport(camera);
        players = map.getPlayers();
        stage = new Stage(sidebarViewport);
        backgroundTexture = new Texture(PANEL);
    }

    void draw(SpriteBatch batch, Stage stage, boolean isOffline, int playerID) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.draw(
                backgroundTexture,
                (int)(WINDOW_HEIGHT * 0.5),
                0,
                (int)(WINDOW_HEIGHT * 0.005 * SIDE_PANEL_PART),
                Gdx.graphics.getHeight()
        );
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        table.row().pad(15, 0, 10, 0);
        TextButton newGameBtn = new TextButton("New Game", SKIN);
        table.add(newGameBtn).fillX().uniformX();
        table.row().pad(25, 0, 10, 0);
        TextButton exitBtn = new TextButton("Exit", SKIN);
        table.add(exitBtn).fillX().uniformX();
        render(0);
        batch.end();
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        sidebarViewport.update((int) (width), height);
    }

    @Override
    public void show() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
