package com.bomberman.server;

import com.bomberman.common.engine.GameServices;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.serialization.Parser;

import java.util.ArrayList;

import static java.lang.System.exit;

import java.io.*;
import java.net.*;

public class ClientPackageSenderThread extends Thread {

    private ArrayList<ObjectOutputStream> outputs;
    private GameServices gameEngine;

    public ClientPackageSenderThread(ArrayList<ObjectOutputStream> outputs, GameServices gameEngine) {
        this.outputs = outputs;
        this.gameEngine = gameEngine;
    }

    public void run() {

        System.out.println("Sender thread started...");

        while (true) {

            if (outputs.isEmpty()) continue;



            //creating package
            Map tempMap = gameEngine.getMap();
            MapDTO packageToSend = new MapDTO(tempMap.getMap(), tempMap.getBombs(), tempMap.getPlayers(), tempMap.getGameTime(), tempMap.getGameStarted());

            //sending package to every user
            for (int i = 0; i < outputs.size(); i++) {

                try {
                    outputs.get(i).writeObject(packageToSend);
                }catch (IOException e){
                    System.out.println("Client " + i + " disconnected");
                    /**
                     *TODO add disconnection
                     */

                }

            }

        }

    }

}
