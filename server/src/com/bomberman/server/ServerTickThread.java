package com.bomberman.server;

import com.bomberman.common.engine.GameServices;

public class ServerTickThread extends Thread {

    private GameServices gameEngine;
    private final int TICK_PER_SECOND = 24;

    public ServerTickThread(GameServices gameEngine) {
        this.gameEngine = gameEngine;
    }

    synchronized public void run() {

        gameEngine.getMap().setTime(0);

        while (true) {
            wait(TICK_PER_SECOND);

            //check if game has started
            if (!gameEngine.getMap().getGameStarted()) continue;

            //tick logic here

            //tick logic end

            //add time
            gameEngine.getMap().addTime((double) 1000 / TICK_PER_SECOND);

            //printing current time
            System.out.println(gameEngine.getMap().getGameTime());
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
