package com.bomberman.client.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

import static com.bomberman.common.utils.EngineUtils.GameState.*;
import static com.bomberman.common.utils.EngineUtils.OFFLINE_PLAYER_INDEX;
import static com.bomberman.common.utils.GraphicUtils.EXPLOSION;

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
    private EngineUtils.GameState state;
    private Sound sound;


    public GameArea(Bomberman game) {
        this.game = game;
        state = IDLE;

        //Create map
        map = new Map();

        //View
        batch = new SpriteBatch();
        stage = new Stage();
        gameCamera = new PerspectiveCamera();
        gameViewport = new ScreenViewport(gameCamera);
        sidePanel = new SidePanel(stage);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorPosition(0,0);
        sound = Gdx.audio.newSound(Gdx.files.internal(EXPLOSION));

        //Communication
        clientServices = new ClientServices(map);
        clientServices.connectToServer();
        isOffline = !clientServices.isConnected();

        //Controller
        controller = new PlayerController(clientServices);

        //Offline mode
        if(isOffline) runGameOffline();
    }

    @Override
    public void render(float delta) {
        checkOnline();
        checkGameState();

        //Main area
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        stage.draw();

        try {
            map.draw(batch);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        soundEffect();
        batch.end();

        //Right-side panel
        sidePanel.draw(batch, isOffline, clientServices.getPlayerId(), map.getPlayers().size(), state);

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
    public void resume() {
        state = IDLE;
    }
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        clientServices.disconnect();
        batch.dispose();
    }

    private void soundEffect() {
        if(map.getSoundEffect().get() > 0) {
            sound.play(1f);
            map.getSoundEffect().decrementAndGet();
        }
    }

    private void playerClick() {
        if(isOffline)
           state = controller.serviceControllerOffline(
                   state,
                   gameServices.getPlayerHandler(OFFLINE_PLAYER_INDEX)
           );
        else state = controller.serviceController(state);
    }

    private void runGameOffline() {
        state = OFFLINE_NOTIFICATION;
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

    public void checkGameState() {
        if(state == DISCONNECTED) return;
        if(!map.getGameStarted()) {
            state = EngineUtils.GameState.IDLE;
            return;
        }
        if(map.getPlayers().size() == 1) {
            if(map.getPlayer(0).getPlayerID() == clientServices.getPlayerId())
                state = WIN;
            else state = LOSS;
            return;
        }
        for(Player p : map.getPlayers()) {
            if(p.getPlayerID() == clientServices.getPlayerId()){
                state = RUNNING;
                return;
            }
        }
        state = LOSS;
    }

    @Override
    public EngineUtils.GameState getGameState() { return state; }

    private void checkOnline() {
        if(isOffline) return;
        if(!clientServices.isConnected()) {
            isOffline = false;
            state = DISCONNECTED;
            game.disconnectGame();
        }
    }
}
