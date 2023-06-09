package com.bomberman.server;


import com.bomberman.common.engine.ClientHandler;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.Map;
import com.bomberman.common.serialization.Parser;

import static java.lang.System.exit;

import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

        //creating map
        Map map = new Map();
        Parser.loadMapFromFile("assets/map.txt", map);

        //test
        System.out.println(map.getMap().size());

        //creating game services
        GameServices gameEngine = new GameServices(map);

        //starting listening for new players
        ClientConnectionHandler connectionHandler = new ClientConnectionHandler(gameEngine);
        connectionHandler.start();

        Scanner keyboardInput = new Scanner(System.in);

        System.out.println("Hello world");

        while (true) {

            String command = keyboardInput.nextLine();
            System.out.println(command);
            if (command.equals("quit")) break;

        }

        System.out.println("Ending server");
        exit(0);
    }
}
