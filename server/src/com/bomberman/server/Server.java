package com.bomberman.server;


import com.bomberman.common.engine.ClientHandler;
import com.bomberman.common.engine.GameServices;
import com.bomberman.common.model.Map;
import com.bomberman.common.serialization.Parser;

import javax.sound.midi.SysexMessage;

import static java.lang.System.exit;

import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

        //creating map
        Map map = new Map();
        Parser.loadMapFromFile("../assets", map);

        //test
        System.out.println(map.getMap().size());

        //creating game services
        GameServices gameEngine = new GameServices(map);

        //creating tick function
        ServerTickThread tickThread = new ServerTickThread(gameEngine);
        tickThread.start();

        //starting listening for new players
        ClientConnectionHandler connectionHandler = new ClientConnectionHandler(gameEngine);
        connectionHandler.start();

        Scanner keyboardInput = new Scanner(System.in);

        System.out.println("Server has started");

        while (true) {

            String command = keyboardInput.nextLine();
            //command list

            //turn off server
            if (command.equals("quit")) break;
            //manual start
            if(command.equals("start")) map.setGameStatus(true);
            //check time
            if(command.equals("time")) System.out.println("Time: "+map.getGameTime());
        }

        System.out.println("Shutting down server");
        exit(0);
    }
}
