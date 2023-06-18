package com.bomberman.server;

import com.bomberman.common.engine.GameServices;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Map;
import com.bomberman.common.model.Player;
import com.bomberman.common.serialization.MapDTO;
import com.bomberman.common.serialization.Parser;
import com.bomberman.common.utils.ClientServerCommunicationUtils;

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

        int packageId = 0;

        System.out.println("Sender thread started...");

        while (true) {

            try {
                //Thread.sleep(2000);
                Thread.sleep(gameEngine.getSendRate());
            } catch (InterruptedException e) {
                System.out.println("Sleep error");
            }

            //System.out.println("Sender- clients connected: " + outputs.size());

            if (outputs.isEmpty()) continue;

            //creating package
            MapDTO packageToSend = new MapDTO(packageId++);
            packageToSend.copy(gameEngine.getMap());

            //sending package to every user
            for (int i = 0; i < outputs.size(); i++) {

                packageToSend.setPlayerId(i);

                try {
                    outputs.get(i).reset();
                    outputs.get(i).writeUnshared(packageToSend);
                } catch (IOException e) {
                    System.out.println("Client #" + i + " write error");
                    e.printStackTrace();
                    /**
                     *TODO add disconnection
                     */
                }
            }//end for loop

        }

    }

}
