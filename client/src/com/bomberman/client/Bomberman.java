package com.bomberman.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Map;

import static com.bomberman.common.utils.GraphicUtils.SIDE_PANEL_PART;

public class Bomberman extends ApplicationAdapter {
	private SpriteBatch batch;
	private Stage stage;
	private Map map;
	private PlayerHandler playerHandler;
	Camera playerOneCamera;
	Camera playerTwoCamera;

	ScreenViewport playerOneViewport;
	ScreenViewport playerTwoViewport;


	@Override
	public void create() {
		batch = new SpriteBatch();
		stage = new Stage();


		playerOneCamera = new PerspectiveCamera();
		playerTwoCamera = new PerspectiveCamera();

		playerOneViewport = new ScreenViewport(playerOneCamera);
		playerTwoViewport = new ScreenViewport(playerTwoCamera);

		Gdx.input.setInputProcessor(stage);
		/*
		Test rysowania mapy - tworzę nową mapę i dodaję elementy
		 */
		map = new Map();
		map.loadMapFromFile("assets/map.txt");
		map.addPlayer(1,1,1);

		/*
		Test obługi gracza - handler będzie obłusgiwał gracza pod indexem 0
		 */
		playerHandler = new PlayerHandler(map.getPlayer(0));
	}

	@Override
	public void render() {
		ScreenUtils.clear(0.25f, 0.75f, 0.55f, 0.75f);

		/*
			Game area
		 */
		batch.setProjectionMatrix(playerOneCamera.combined);
		batch.begin();
		stage.draw();
		playerHandler.serviceController();
		map.draw(batch);
		batch.end();


		/*
			Right-side panel
		 */
		batch.setProjectionMatrix(playerTwoCamera.combined);
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height){
		playerOneViewport.update((int)(width * SIDE_PANEL_PART * 0.01), height);
		playerTwoViewport.update((int)(width * (1 - SIDE_PANEL_PART * 0.01)), height);
	}
}
