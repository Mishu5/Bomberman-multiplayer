package com.bomberman.client.gui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;

import java.util.ArrayList;

import static com.bomberman.common.utils.GraphicUtils.MENU;
import static com.bomberman.common.utils.GraphicUtils.SIDE_PANEL_PART;

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
        backgroundTexture = new Texture(MENU);
    }

    void draw(SpriteBatch batch, boolean isOffline) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        render(0);
        batch.end();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        sidebarViewport.update((int) (width * (1 - SIDE_PANEL_PART * 0.01)), height);
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
