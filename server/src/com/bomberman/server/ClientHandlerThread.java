package com.bomberman.server;

import com.bomberman.common.engine.PlayerHandler;
import java.io.*;
import java.net.*;

public class ClientHandlerThread extends Thread{

    private final PlayerHandler playerHandler;
    private final BufferedReader in;

    public ClientHandlerThread(PlayerHandler playerHandler, BufferedReader in){
        this.playerHandler = playerHandler;
        this.in = in;
    }

    synchronized public void run(){

        System.out.println("CLient " + playerHandler.getID() + " listener");

        while(true){

        }

    }



}
