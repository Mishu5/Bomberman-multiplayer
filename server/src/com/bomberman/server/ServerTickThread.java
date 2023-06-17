package com.bomberman.server;

import com.bomberman.common.engine.GameServices;

public class ServerTickThread extends Thread {

    private GameServices gameEngine;

    public ServerTickThread(GameServices gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void run(){



    }

}
