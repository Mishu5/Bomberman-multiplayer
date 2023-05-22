package com.bomberman.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bomberman.common.model.Map;

public class Bomberman extends ApplicationAdapter {
	private SpriteBatch batch;
	private Map map;


	@Override
	public void create() {
		batch = new SpriteBatch();

		/*
		Test rysowania mapy - tworzę nową mapę i dodaję elementy
		 */
		map = new Map();
		map.loadMapFromFile("map.txt");
		map.addPlayer(1,1,1);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0.25f, 0.75f, 0.55f, 1);

		batch.begin();
		map.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
