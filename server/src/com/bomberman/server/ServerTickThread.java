package com.bomberman.server;

import com.bomberman.common.engine.GameServices;
import com.bomberman.common.utils.ClientServerCommunicationUtils;

public class ServerTickThread extends Thread {

    private GameServices gameEngine;

    public ServerTickThread(GameServices gameEngine) {
        this.gameEngine = gameEngine;
    }

    synchronized public void run() {

        gameEngine.getMap().setTime(0);

        while (true) {
            wait(ClientServerCommunicationUtils.GAME_TICK_RATE);

            //check if game has started
            if (!gameEngine.getMap().getGameStarted()) continue;

            //tick logic here

            //tick logic end

            //add time
            gameEngine.getMap().addTime(1f);
        }

    }

    private void wait(int ticks) {
        try {
            Thread.sleep(1000 / ticks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
