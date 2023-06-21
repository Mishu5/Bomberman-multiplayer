package com.bomberman.client.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.common.utils.EngineUtils;

import static com.bomberman.common.utils.GraphicUtils.*;

public class SidePanel implements Screen {
    private final ScreenViewport sidebarViewport;
    private final Camera camera;
    private final Texture backgroundTexture;
    private final Stage stage;

    SidePanel(Stage stage) {
        this.stage = stage;
        camera = new PerspectiveCamera();
        sidebarViewport = new ScreenViewport(camera);
        backgroundTexture = getTexture(PANEL);
    }

    synchronized void draw(
            SpriteBatch batch,
            boolean isOffline,
            int playerID,
            int playersAmount,
            EngineUtils.GameState state
    ) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(
                backgroundTexture,
                (int)(WINDOW_HEIGHT * 0.5),
                0,
                (int)(WINDOW_HEIGHT * 0.005 * SIDE_PANEL_PART),
                Gdx.graphics.getHeight()
        );
        render(0);

        Texture playersLabel = getTexture(PLAYERS_LABEL[Math.max(0, playersAmount - 1) % PLAYERS_LABEL.length]);
        batch.draw(
                playersLabel,
                (int)(WINDOW_HEIGHT * 0.5) + 70,
                (int)(WINDOW_HEIGHT * 0.25) + 100,
                50,
                50
        );
        render(0);

        Texture playerCharacter = getScaledTexture(PLAYER_TEXTURES[playerID]);
        batch.draw(
                playerCharacter,
                (int)(WINDOW_HEIGHT * 0.5) + 70,
                (int)(WINDOW_HEIGHT * 0.25) - 150,
                50,
                50
        );
        render(0);

        Texture connection = state == EngineUtils.GameState.RUNNING ? getTexture(CONNECTED) : getTexture(DISCONNECTED);
        batch.draw(
                connection,
                (int)(WINDOW_HEIGHT * 0.5) + 5,
                (int)(WINDOW_HEIGHT * 0.25) + 150,
                200,
                200
        );
        render(0);

        if(playerID == 0 && !isOffline && playersAmount > 1 && state == EngineUtils.GameState.IDLE) {
            Texture enter = getTexture(ENTER);
            batch.draw(
                    enter,
                    (int)(WINDOW_HEIGHT * 0.5 + 15),
                    (int)(WINDOW_HEIGHT * 0.25 - 10),
                    165,
                    80
            );
            render(0);
        }

        batch.end();
    }

    @Override
    public void render(float delta) {
        stage.draw();
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
