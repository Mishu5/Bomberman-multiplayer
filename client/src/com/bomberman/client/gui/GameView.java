package com.bomberman.client.gui;

import com.badlogic.gdx.Screen;
import com.bomberman.common.utils.EngineUtils;

public interface GameView extends Screen {
    EngineUtils.GameState getGameState();
}
