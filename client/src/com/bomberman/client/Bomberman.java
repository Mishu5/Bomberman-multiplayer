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
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.Parser;

import static com.bomberman.common.utils.GraphicUtils.SIDE_PANEL_PART;
import static java.lang.System.exit;

public class Bomberman extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Map map;
    private Camera gameCamera;
    private Camera sidebarCamera;
    private ScreenViewport gameViewport;
    private ScreenViewport sidebarViewport;
    private GameServices services;
    private ClientServices clientServices;
    private PlayerController controller;
    private boolean isOffline;

    int playerID = 1; //moved to clientServices

    @Override
    public void create() {

        //View
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        gameCamera = new PerspectiveCamera();
        sidebarCamera = new PerspectiveCamera();
        gameViewport = new ScreenViewport(gameCamera);
        sidebarViewport = new ScreenViewport(sidebarCamera);

        //Create map
        map = new Map();

        //Communication
        clientServices = new ClientServices(map);
        clientServices.connectToServer();
        isOffline = !clientServices.isConnected();
        if(isOffline) {
            System.out.println("Server is offline. Try to run server.");
            //exit(1);
        }

        //Controller
        controller = new PlayerController(clientServices);

        //Offline mode
        if(!isOffline) return;
        Parser parser = new Parser();
        parser.loadMapFromFile("../assets", map);

        services = new GameServices(map);
        services.addPlayer(new Player(4, 2, playerID));
        services.addPlayer(new Player(16, 2, 2));
        services.addPlayer(new Player(4, 17, 3));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.25f, 0.75f, 0.55f, 0.75f);

        //Game area
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        stage.draw();

        if(isOffline) controller.serviceControllerOffline(services.getPlayerHandler(1));
        else controller.serviceController();

        try {
            map.draw(batch);
        } catch (Exception e) { e.printStackTrace(); }

        batch.end();

        //Right-side panel
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