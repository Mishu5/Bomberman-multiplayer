package com.bomberman.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.Parser;

import static com.bomberman.common.utils.EngineUtils.PLAYER_SPEED;

public class Bomberman extends ApplicationAdapter {
	private SpriteBatch batch;
	private Stage stage;
	private Map map;
	private PlayerHandler playerHandler;


	@Override
	public void create() {
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
        /*
		Test rysowania mapy - tworzę nową mapę i dodaję elementy
		 */
		map = new Map();
		Parser.loadMapFromFile("../assets/map.txt", map);
		map.addPlayer(1,1,1);

        /*
		Test obługi gracza - handler będzie obłusgiwał gracza pod indexem 0
		 */
		playerHandler = new PlayerHandler(map.getPlayer(0));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1,1,1,0);
		ScreenUtils.clear(0.25f, 0.75f, 0.55f, 1);
		batch.begin();

		stage.draw();

		if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
			playerHandler.characterMove(0, 1);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
			playerHandler.characterMove(0, -1);
		if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
			playerHandler.characterMove(-1, 0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			playerHandler.characterMove(1, 0);
		map.draw(batch);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
