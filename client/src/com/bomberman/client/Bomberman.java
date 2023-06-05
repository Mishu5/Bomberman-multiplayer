package com.bomberman.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.Bomb;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.Parser;

import static com.bomberman.common.utils.EngineUtils.DETONATION_RADIUS;
import static com.bomberman.common.utils.GraphicUtils.SIDE_PANEL_PART;

public class Bomberman extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Map map;
    private Camera gameCamera;
    private Camera sidebarCamera;
    private ScreenViewport gameViewport;
    private ScreenViewport sidebarViewport;

    private GameServices services;

    int playerID = 1;


    @Override
    public void create() {
		/*
			View objects
		 */
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        gameCamera = new PerspectiveCamera();
        sidebarCamera = new PerspectiveCamera();
        gameViewport = new ScreenViewport(gameCamera);
        sidebarViewport = new ScreenViewport(sidebarCamera);

		/*
			Create map
		 */
        map = new Map();
        Parser parser = new Parser();
        parser.loadMapFromFile("map.txt", map);

		/*
			Player assign
		 */
        services = new GameServices(map);
        services.addPlayer(new Player(2, 3, playerID));

		/*
			Test bomb
		 */
        services.addBomb(new Bomb(2, 2, DETONATION_RADIUS));

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.25f, 0.75f, 0.55f, 0.75f);

		/*
			Game area
		 */
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        stage.draw();
        services.serviceController(playerID);
        map.draw(batch);
        batch.end();


		/*
			Right-side panel
		 */
        batch.setProjectionMatrix(sidebarCamera.combined);
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update((int) (width * SIDE_PANEL_PART * 0.01), height);
        sidebarViewport.update((int) (width * (1 - SIDE_PANEL_PART * 0.01)), height);
    }
}