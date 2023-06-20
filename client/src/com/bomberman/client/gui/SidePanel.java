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
    private Table sidebar;

    SidePanel(Map map) {
        camera = new PerspectiveCamera();
        sidebarViewport = new ScreenViewport(camera);
        players = map.getPlayers();
        stage = new Stage(sidebarViewport);
        backgroundTexture = new Texture(PANEL);
        sidebar = new Table();
        sidebar.setFillParent(true);
        sidebar.setDebug(false);
        stage.addActor(sidebar);

        sidebar.row().pad(15, 0, 10, 0);
        sidebar.row().pad(25, 0, 10, 0);
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
        //sidebar.draw(batch, 0);

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
