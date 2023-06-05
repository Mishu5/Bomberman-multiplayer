package com.bomberman.server;

import com.bomberman.common.engine.PlayerHandler;

public class ClientHandlerThread extends Thread{

    private final PlayerHandler playerHandler;

    public ClientHandlerThread(PlayerHandler playerHandler){
        this.playerHandler = playerHandler;
    }


}
