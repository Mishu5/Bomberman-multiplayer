package com.bomberman.server;


import com.bomberman.common.engine.ClientHandler;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.Map;
import com.bomberman.common.serialization.Parser;

import static java.lang.System.exit;

public class Server {
    public static void main(String[] args) {

        //creating map
        Map map = new Map();
        Parser.loadMapFromFile("assets/map.txt", map);

        //creating game services
        GameServices gameEngine = new GameServices(map);

        //starting listening for new players
        ClientConnectionHandler connectionHandler= new ClientConnectionHandler(gameEngine);
        connectionHandler.start();
        connectionHandler.setDaemon(true);

        System.out.println("Hello world");
        exit(0);
    }
}
