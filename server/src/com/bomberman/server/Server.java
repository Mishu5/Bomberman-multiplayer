package com.bomberman.server;


import com.bomberman.common.engine.ClientHandler;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.Map;
import com.bomberman.common.serialization.Parser;

import static java.lang.System.exit;

public class Server{
    public static void main(String[] args) {

        //creating map
        Map map = new Map();
        Parser.loadMapFromFile("assets/map.txt", map);

        //creating game services
        GameServices gameEngine = new GameServices(map);

        //starting listening for new players
        ClientConnectionHandler connectionHandler= new ClientConnectionHandler(gameEngine);
        connectionHandler.start();

        System.out.println("Hello world");
        try {
            Thread.sleep(50000);
        }catch (InterruptedException e){
            System.out.println("ERROR");
        }
        System.out.println("Ending server");
        exit(0);
    }
}
