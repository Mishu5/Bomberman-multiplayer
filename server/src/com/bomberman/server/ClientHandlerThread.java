package com.bomberman.server;

import com.bomberman.common.engine.PlayerHandler;
import com.bomberman.common.utils.EngineUtils;

import java.io.*;
import java.net.*;

import static java.lang.System.exit;
import static com.bomberman.common.utils.EngineUtils.*;

public class ClientHandlerThread extends Thread{

    private final PlayerHandler playerHandler;
    private final BufferedReader in;

    private final int INPUT_RECEIVING_ERROR = -1;

    private final int NO_INPUT = 0;
    private final int UP = 1;
    private final int RIGHT = 2;
    private final int DOWN = 3;
    private final int LEFT = 4;
    private final int BOMB = 5;

    public ClientHandlerThread(PlayerHandler playerHandler, BufferedReader in){
        this.playerHandler = playerHandler;
        this.in = in;
    }

    synchronized public void run(){

        System.out.println("Client " + playerHandler.getID() + " listener");

        while(true){

            int currentClientInput = getInputFromPlayer();
            if(currentClientInput == INPUT_RECEIVING_ERROR){
                /**
                 * TOOD remove player
                 */
                return;
            }

           switch (currentClientInput){
               case UP:
                        playerHandler.moveAttempt(1,0, Direction.TOP);
                   break;
               case RIGHT:
                        playerHandler.moveAttempt(0,1,Direction.RIGHT);
                   break;
               case DOWN:
                        playerHandler.moveAttempt(-1,0,Direction.BOT);
                   break;

               case LEFT:
                        playerHandler.moveAttempt(0,-1,Direction.LEFT);
                   break;

               case BOMB:
                        playerHandler.putBombAttempt();
                   break;
               default:

                   break;
           }

        }//end while

    }

    private int getInputFromPlayer(){

        int currentInput;
        String tempInput = null;

        try{
            tempInput = in.readLine();
        }catch (IOException e){
            System.out.println("Client " + playerHandler.getID() + "Input error");
            return(INPUT_RECEIVING_ERROR);
        }

        switch(tempInput){
            case "w":
                currentInput = UP;
                break;
            case "d":
                currentInput = RIGHT;
                break;
            case "s":
                currentInput = DOWN;
                break;
            case "a":
                currentInput = LEFT;
                break;
            case "b":
                currentInput = BOMB;
                break;
            default:
                currentInput = NO_INPUT;
                break;
        }

        return currentInput;
    }

}
