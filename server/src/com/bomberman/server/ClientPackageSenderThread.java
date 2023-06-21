package com.bomberman.server;

import com.bomberman.common.engine.GameServices;

import com.bomberman.common.serialization.MapDTO;


import java.util.ArrayList;


import java.io.*;


public class ClientPackageSenderThread extends Thread {

    private ArrayList<ObjectOutputStream> outputs;
    private GameServices gameEngine;
    private ArrayList<ObjectOutputStream> disconnected;

    public ClientPackageSenderThread(ArrayList<ObjectOutputStream> outputs, GameServices gameEngine) {
        this.outputs = outputs;
        this.gameEngine = gameEngine;
        this.disconnected = new ArrayList<>();
    }

    public void run() {

        int packageId = 0;

        System.out.println("Sender thread started...");

        while (true) {

            try {

                Thread.sleep(gameEngine.getSendRate());
            } catch (InterruptedException e) {
                System.out.println("Sleep error");
            }


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

                    //Client disconnected
                    System.out.println("Client #" + i + " disconnected");
                    disconnected.add(outputs.get(i));
                }
            }//end for loop

            while (!disconnected.isEmpty()) {
                outputs.remove(disconnected.get(0));
                disconnected.remove(0);
            }

        }//end while loop

    }

}
