package com.bomberman.server;

import com.bomberman.common.engine.GameServices;
import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.model.Player;
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

        while (true) {

            if (outputs.isEmpty()) continue;

        }

    }

}
