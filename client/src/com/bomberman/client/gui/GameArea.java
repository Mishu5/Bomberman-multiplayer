package com.bomberman.client.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.client.Bomberman;
import com.bomberman.client.communication.ClientServices;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.*;
import com.bomberman.common.serialization.Parser;
import com.bomberman.common.utils.EngineUtils;

import static com.bomberman.common.utils.EngineUtils.OFFLINE_PLAYER_INDEX;
import static com.bomberman.common.utils.GraphicUtils.SIDE_PANEL_PART;

public class GameArea implements Screen, GameView{
    private final Camera gameCamera;
    private final ScreenViewport gameViewport;
    private final SpriteBatch batch;
    private final Stage stage;
    private final PlayerController controller;
    private GameServices gameServices;
    private final ClientServices clientServices;
    private final Map map;
    private boolean isOffline;
    private final SidePanel sidePanel;
    private final Bomberman game;

    public GameArea(Bomberman game) {
        this.game = game;

        //Create map
        map = new Map();

        //View
        gameCamera = new PerspectiveCamera();
        gameViewport = new ScreenViewport(gameCamera);
        sidePanel = new SidePanel(map);
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorPosition(0,0);

        //Communication
        clientServices = new ClientServices(map);
        clientServices.connectToServer();
        isOffline = !clientServices.isConnected();
        if(isOffline) game.offlineMode();

        //Controller
        controller = new PlayerController(clientServices);

        //Offline mode
        if(isOffline) runGameOffline();
    }

    @Override
    public void render(float delta) {
        checkOnline();

        //Main area
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        stage.draw();

        try {
            map.draw(batch);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        batch.end();

        //Right-side panel
        sidePanel.draw(batch, stage, isOffline);

        //Input
        playerClick();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update((int) (width), height);
        sidePanel.resize(width, height);
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
    public void dispose() {
        clientServices.disconnect();
        batch.dispose();
    }

    private void runGameOffline() {
        Parser.loadMapFromFile("../assets", map);
        map.setGameStatus(true);
        gameServices = new GameServices(map);

        for (MapObject obiekt : map.getMap()) {
            if (obiekt instanceof Spawn) {
                Spawn spawn = (Spawn) obiekt;
                gameServices.addPlayer(new Player(spawn.getPositionX(), spawn.getPositionY(), spawn.getSpawnID()));
            }
        }
    }

    private void playerClick() {
        if(isOffline)
            controller.serviceControllerOffline(gameServices.getPlayerHandler(OFFLINE_PLAYER_INDEX));
        else controller.serviceController();
    }

    @Override
    public EngineUtils.GameState getGameState() {
        if(map.getPlayers() == null) return EngineUtils.GameState.IDLE;
        if(map.getPlayers().size() == 1) {
            if(map.getPlayer(0).getPlayerID() == clientServices.getPlayerId()) {
                //System.out.println("You won!");
                return EngineUtils.GameState.WIN;
            }
            else {
                //System.out.println("Player " + map.getPlayer(0).getPlayerID() + " won!");
                return EngineUtils.GameState.LOSS;
            }
        }
        for(Player p : map.getPlayers()) {
            if(p.getPlayerID() == clientServices.getPlayerId()) return EngineUtils.GameState.RUNNING;
        }
        //System.out.println("You died!");
        return EngineUtils.GameState.RUNNING;
    }

    private void checkOnline() {
        if(isOffline) return;
        if(!clientServices.isConnected()) {
            isOffline = false;
            game.disconnectGame();
        }
    }
}
