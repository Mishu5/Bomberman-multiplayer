package com.bomberman.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.*;
import com.bomberman.common.serialization.Parser;
import com.bomberman.common.utils.EngineUtils;

import static com.bomberman.common.utils.GraphicUtils.SIDE_PANEL_PART;

public class GameArea implements Screen {
    private final Camera gameCamera;
    private final Camera sidebarCamera;
    private final ScreenViewport gameViewport;
    private final ScreenViewport sidebarViewport;
    private final SpriteBatch batch;
    private final Stage stage;
    private final PlayerController controller;
    private GameServices gameServices;
    private final ClientServices clientServices;
    private final Map map;
    private final boolean isOffline;
    private final Bomberman game;

    GameArea(Bomberman game) {
        this.game = game;

        //View
        gameCamera = new PerspectiveCamera();
        sidebarCamera = new PerspectiveCamera();
        gameViewport = new ScreenViewport(gameCamera);
        sidebarViewport = new ScreenViewport(sidebarCamera);
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //Create map
        map = new Map();

        //Communication
        clientServices = new ClientServices(map);
        clientServices.connectToServer();
        isOffline = !clientServices.isConnected();
        if(isOffline) {
            System.out.println("Server is offline. Try to run server.");
        }

        //Controller
        controller = new PlayerController(clientServices);

        //Offline mode
        if(isOffline) runGameOffline();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Game area
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        stage.draw();

        if(isOffline) controller.serviceControllerOffline(gameServices.getPlayerHandler(0));
        else controller.serviceController();

        try {
            map.draw(batch);
        } catch (Exception e) { e.printStackTrace(); }

        batch.end();

        //Right-side panel
        batch.setProjectionMatrix(sidebarCamera.combined);
        batch.begin();
        batch.end();

        //getGameState();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update((int) (width * SIDE_PANEL_PART * 0.01), height);
        sidebarViewport.update((int) (width * (1 - SIDE_PANEL_PART * 0.01)), height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public EngineUtils.GameState getGameState() {
        if(map.getPlayers() == null) return EngineUtils.GameState.IDLE;
        if(map.getPlayers().size() == 1) {
            if(map.getPlayer(0).getPlayerID() == clientServices.getPlayerId()) {
                System.out.println("You won!");
                return EngineUtils.GameState.WIN;
            }
            else {
                System.out.println("Player " + map.getPlayer(0).getPlayerID() + " won!");
                return EngineUtils.GameState.LOSS;
            }
        }
        for(Player p : map.getPlayers()) {
            if(p.getPlayerID() == clientServices.getPlayerId()) return EngineUtils.GameState.RUNNING;
        }
        System.out.println("You died!");
        return EngineUtils.GameState.RUNNING;
    }

    private void runGameOffline() {
        Parser.loadMapFromFile("../assets", map);
        map.setGameStatus(true);
        gameServices = new GameServices(map);

        //Szybka funkcja do spawnowania graczy na ich miejscach, nie wiem gdzie ja chcecie przeniesc xD
        for (MapObject obiekt : map.getMap()) {
            if (obiekt instanceof Spawn) {
                Spawn spawn = (Spawn) obiekt;
                gameServices.addPlayer(new Player(spawn.getPositionX(), spawn.getPositionY(), spawn.getSpawnID()));
            }
        }
    }
}
